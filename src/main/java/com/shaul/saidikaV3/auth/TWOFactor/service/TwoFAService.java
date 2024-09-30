package com.shaul.saidikaV3.auth.TWOFactor.service;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.auth.AuthToken.AuthorizationToken;
import com.shaul.saidikaV3.auth.AuthToken.TokenRepository;
import com.shaul.saidikaV3.auth.TOTPAuthenticator;
import com.shaul.saidikaV3.auth.TWOFactor.MissingTOTPKeyAuthenticatorException;
import com.shaul.saidikaV3.auth.TWOFactor.TwoFactor;
import com.shaul.saidikaV3.auth.TWOFactor.TwoFactorRepository;
import com.shaul.saidikaV3.configs.SecurityConfig;
import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.requestModels.VerifyCodeRequest;
import com.shaul.saidikaV3.utils.ConvertionUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TwoFAService {

    @Autowired
    private AuthService authService;


    @Autowired
    private TwoFactorRepository twoFactorRepository;

    @Autowired
    private TOTPAuthenticator totpAuthenticator;

    @Autowired
    private Environment env;


    @Autowired
    private TokenRepository tokenRepository;



    public ResponseEntity<String> status() {
        Users user=authService.getActiveProfile();
        Optional<TwoFactor> optionalTwoFactor=twoFactorRepository.findByUser_id(user.getId());
        if(optionalTwoFactor.isPresent()&&optionalTwoFactor.get().getIsVerified())
            return ResponseEntity.ok("Enabled");
        else
            return ResponseEntity.ok("Disabled");
    }


    public ResponseEntity<String> disable() {

        Users user=authService.getActiveProfile();
        Optional<TwoFactor> optionalTwoFactor=twoFactorRepository.findByUser_id(user.getId());
        if(optionalTwoFactor.isPresent())
        {
            twoFactorRepository.delete(optionalTwoFactor.get());
            return ResponseEntity.ok("Disabled Successfully");
        }
        return ResponseEntity.ok("Was Already Disabled");
    }

    public String setup2FA() {

        try {
            return generateQRCode(generateOTPProtocol());
        } catch (Throwable e) {

                        return "Error";
        }


    }

    public String generateOTPProtocol() {
        Users user = authService.getActiveProfile();
        String secret=totpAuthenticator.generateSecret();
        TwoFactor twoFactor= TwoFactor.builder()
                .user(user)
                .secret(secret)
                .isVerified(false)
                .build();
        twoFactorRepository.saveAndFlush(twoFactor);
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", user.getFirst_name(), user.getLast_name() + "@devapp.com", secret,env.getRequiredProperty("2fa.application.name"));
    }

    public String generateQRCode(String otpProtocol) throws Throwable {
        return totpAuthenticator.generateQRCode(otpProtocol);
    }

    public boolean validateTotp(String secret, Integer totpKey) {
        if (StringUtils.hasText(secret)) {
            if (totpKey != null) {
                try {
                    if (!totpAuthenticator.verifyCode(secret, totpKey, Integer.parseInt(env.getRequiredProperty("2fa.application.time")))) {
                        System.out.printf("Code %d was not valid", totpKey);
                        throw new BadCredentialsException(
                                "Invalid TOTP code");
                    }
                } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new InternalAuthenticationServiceException(
                            "TOTP code verification failed", e);
                }
            } else {
                throw new MissingTOTPKeyAuthenticatorException(
                        "TOTP code is mandatory");
            }
        }
        System.out.println("Code Successful");
        return true;
    }

    @SuppressWarnings({ "deprecation", "unused" })
    public ResponseEntity<String> verify2FA(VerifyCodeRequest verifyCodeRequest, HttpServletRequest request) {

        String authorization=request.getHeader("Authorization");
        String init="Bearer ";
        Boolean filterSuccess=false;
        if(authorization!=null&&authorization.contains(init)) {
//            try {
            String tokenString = authorization.replaceAll(init, "");
            Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConfig.jwtSecretKey).build().parseClaimsJws(tokenString);
            String username = claims.getBody().getSubject();

            Optional<AuthorizationToken> tmpToken=tokenRepository.findByValue(tokenString);
            if(tmpToken.isPresent()) {
                AuthorizationToken token = tmpToken.get();
                String secret=twoFactorRepository.findByUser_id(token.getProfileid()).get().getSecret();

                if(validateTotp(secret, ConvertionUtils.getInt(verifyCodeRequest.getVerificationCode())))
                {
                    token.setAuthenticated(true);
                    tokenRepository.saveAndFlush(token);
                    return ResponseEntity.ok("Verification Successful");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Verification Failed");
    }

    @SuppressWarnings({"deprecation", "unused" })
    public ResponseEntity<String> enable2FA(VerifyCodeRequest verifyCodeRequest, HttpServletRequest request) {

        String authorization=request.getHeader("Authorization");
        String init="Bearer ";
        Boolean filterSuccess=false;
        if(authorization!=null&&authorization.contains(init)) {
//            try {
            String tokenString = authorization.replaceAll(init, "");
            Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConfig.jwtSecretKey).build().parseClaimsJws(tokenString);
          
            String username = claims.getBody().getSubject();

            Optional<AuthorizationToken> tmpToken=tokenRepository.findByValue(tokenString);
            if(tmpToken.isPresent()) {
                AuthorizationToken token = tmpToken.get();
                TwoFactor twoFactor = twoFactorRepository.findByUser_id(token.getProfileid()).get();
                String secret=twoFactor.getSecret();

                if(validateTotp(secret, ConvertionUtils.getInt(verifyCodeRequest.getVerificationCode())))
                {
                    token.setAuthenticated(true);
                    twoFactor.setIsVerified(true);
                    twoFactorRepository.saveAndFlush(twoFactor);
                    tokenRepository.saveAndFlush(token);
                    return ResponseEntity.ok("Verification Successful");
                }

            }

        }
        return ResponseEntity.status(HttpStatus.SC_NOT_ACCEPTABLE).body("Verification Failed");
    }
}
