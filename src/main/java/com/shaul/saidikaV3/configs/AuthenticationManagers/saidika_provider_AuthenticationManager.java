package com.shaul.saidikaV3.configs.AuthenticationManagers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class saidika_provider_AuthenticationManager implements AuthenticationManager {
     private final AuthenticationProvider authenticationProvider;

    public saidika_provider_AuthenticationManager (AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationProvider.authenticate(authentication);
    }
}
