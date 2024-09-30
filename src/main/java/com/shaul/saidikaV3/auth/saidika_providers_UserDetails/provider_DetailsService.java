package com.shaul.saidikaV3.auth.saidika_providers_UserDetails;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.auth.Authorities.AccountRoles;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.repositories.service_provider_repo;

import java.util.Optional;
import java.util.Set;

@Service
public class provider_DetailsService implements UserDetailsService {

    @Autowired
    private service_provider_repo provider_repository;

    @Override
    public provider_UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<service_provider> optional_service_provider = provider_repository.findByEmail(username);
        if(optional_service_provider.isEmpty())
            throw new UsernameNotFoundException("user Not Found");

            service_provider user= optional_service_provider.get();


        return provider_UserDetails.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(Set.of(new SimpleGrantedAuthority(AccountRoles.PROVIDER.name())))
                .build();

    }
}
