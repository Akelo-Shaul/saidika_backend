package com.shaul.saidikaV3.auth;






import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.auth.AuthToken.AuthorizationToken;
import com.shaul.saidikaV3.auth.AuthToken.TokenRepository;
import com.shaul.saidikaV3.auth.Authorities.AccountAuthority;
import com.shaul.saidikaV3.auth.Authorities.AccountRoles;
import com.shaul.saidikaV3.auth.saidika_finders_UserDetails.finder_DetailsService;
import com.shaul.saidikaV3.auth.saidika_providers_UserDetails.provider_DetailsService;
import com.shaul.saidikaV3.configs.SecurityConfig;
import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.repositories.service_finder_repo;
import com.shaul.saidikaV3.repositories.service_provider_repo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private finder_DetailsService Finder_DetailsService;

    @Autowired
    private provider_DetailsService providerDetailsService;

 

 




    @Autowired
    private service_finder_repo frRepository;

    @Autowired
    private service_provider_repo prRepository;
;

    public void authorizeRequest(String tokenString, HttpServletRequest request, String username)
    {
        Optional<AuthorizationToken> tmpToken=tokenRepository.findByValue(tokenString);
        if(tmpToken.isPresent()) {
            AuthorizationToken token=tmpToken.get();
            if (token.isUsable()) {

               
               //if(check2FA(token)) {
                   if (!request.getRequestURL().toString().endsWith("/checkProfile")) {
                        token.setLastAccess(new Date().getTime());
                        tokenRepository.save(token);
                   }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(username, token);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //}
            
            
            }
        }
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String username, AuthorizationToken token) {
        UserDetailsService detailsService;

        if(token.getAccountRole()==AccountRoles.FINDER)
            detailsService = Finder_DetailsService;
        else 
            detailsService = providerDetailsService;
          

        UserDetails userDetails = detailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(), userDetails.getAuthorities());
        return usernamePasswordAuthenticationToken;
    }

    // private boolean check2FA(AuthorizationToken token) {
    //     if(get2FAEnabled(token.getProfileid(),token.getAccountRole()))
    //     {
    //         return (token.getAuthenticated()!=null&&token.getAuthenticated());

    //     }else
    //         return true;
    // }

//     public Boolean get2FAEnabled(UUID profileId, AccountRoles accountRole)
//     {
//         Optional<TwoFactor> optionalTwoFactor=twoFactorRepository.findByUserUidAndAccountRole(profileId,accountRole);
//        return optionalTwoFactor.isPresent()&&optionalTwoFactor.get().getIsVerified();
//         return false;
//     }

    @SuppressWarnings("unchecked")
    public Users getActiveProfile()
    {
//        SecurityContextHolder.getContext().getAuthentication().getAuthorities().eq
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        //        Optional<Person> optionalPerson;
        Collection<GrantedAuthority> authorities= (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("authorities" + authorities);

        if(authorities.contains(new AccountAuthority(AccountRoles.FINDER.name()))) {
            Optional<service_finder> optionalPerson = frRepository.findByEmail(username);
            if(optionalPerson.isPresent())
                return optionalPerson.get();
        }
       
        else if(authorities.contains(new AccountAuthority(AccountRoles.PROVIDER.name()))){
            Optional<service_provider> optionalPerson = prRepository.findByEmail(username);
            if(optionalPerson.isPresent())
                return optionalPerson.get();
        }

        return null;
    }


    @SuppressWarnings("deprecation")
    public String loginUser(UUID uid,String username, String password,AuthenticationManager authenticationManager,AccountRoles accountRole) {
//        System.out.println("Loggin in "+num+" - "+password);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

//        System.out.println("success Loggin in "+num+" - "+password);
        HashMap<String,String> claims=new HashMap<>();
        claims.put(AccountRoles.class.getSimpleName(),accountRole.name());
        String token = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(168)))
                .signWith(SignatureAlgorithm.HS256, SecurityConfig.jwtSecretKey).compact();

        AuthorizationToken authorizationTokens=AuthorizationToken.builder()
                .lastAccess(new Date().getTime())
                .loggedOut(false)
                .inActiveTime(TimeUnit.MINUTES.toMillis(10))
                .value(token)
                .accountRole(accountRole)
                .profileid(uid)
                .authenticated(true)
                .build();

        tokenRepository.saveAndFlush(authorizationTokens);

        String authorization="Bearer "+token;

        return authorization;
    }


    public String logOut(HttpServletRequest request)
    {
        String authorization=request.getHeader("Authorization");
        String init="Bearer ";
        if(authorization!=null&&authorization.contains(init)) {
            String tokenString = authorization.replaceAll(init, "");
            Optional<AuthorizationToken> optionalAuthorizationTokens = tokenRepository.findByValue(tokenString);
            if (optionalAuthorizationTokens.isEmpty())
                throw new UnsupportedOperationException("no such authorization");

            AuthorizationToken authorizationTokens = optionalAuthorizationTokens.get();

            //perform the logout action
            authorizationTokens.setLoggedOut(true);
            tokenRepository.save(authorizationTokens);

            return "Success";
        }
        else
            throw new UnsupportedOperationException("no such authorization");
    }

    public String logOutAllDevices()
    {
            List<AuthorizationToken> allActiveProfileTokens = tokenRepository.findByProfileid(getActiveProfile().getId());
            for(AuthorizationToken authorizationTokens:allActiveProfileTokens) {
                //perform the logout action
                authorizationTokens.setLoggedOut(true);
                tokenRepository.save(authorizationTokens);
            }
            return "Success";
    }

    public Long lastSeen(UUID profileId)
    {
        Long lastAccess;
        lastAccess=tokenRepository.findLatestAccess(profileId);
        if(lastAccess==null)
            lastAccess=0L;
        return  lastAccess;
    }


}
