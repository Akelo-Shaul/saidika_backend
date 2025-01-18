package com.shaul.saidikaV3.auth;




import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shaul.saidikaV3.configs.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTTOkenFilter extends OncePerRequestFilter {


    @Autowired
    private AuthService authService;




    @SuppressWarnings("deprecation")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization=request.getHeader("Authorization");
        String init="Bearer ";
        if(authorization!=null&&authorization.contains(init))
        {
            try {
                String tokenString = authorization.replaceAll(init, "");
                Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConfig.jwtSecretKey).build().parseClaimsJws(tokenString);
                String profilename = claims.getBody().getSubject();
                if (profilename != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Date expiry = claims.getBody().getExpiration();
                    Date current = new Date();
                    if (current.before(expiry)) {
                        authService.authorizeRequest(tokenString, request, profilename);
                    }
                }
            }catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SecurityException | IllegalArgumentException es)
            {

            }
        }
        filterChain.doFilter(request, response);
    }

}
