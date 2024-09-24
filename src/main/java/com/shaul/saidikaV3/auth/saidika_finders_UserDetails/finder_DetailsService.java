package com.shaul.saidikaV3.auth.saidika_finders_UserDetails;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.auth.Authorities.AccountRoles;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.repositories.service_finder_repo;

import java.util.Optional;
import java.util.Set;

@Service
public class finder_DetailsService implements UserDetailsService {

    @Autowired
    private service_finder_repo finder_repository;

    @Override
    public finder_UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<service_finder> optional_service_finder = finder_repository.findByEmail(username);
        if(optional_service_finder.isEmpty())
            throw new UsernameNotFoundException("user Not Found");

            service_finder user= optional_service_finder.get();


        return finder_UserDetails.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(Set.of(new SimpleGrantedAuthority(AccountRoles.SERVICE_FINDER.name())))
                .build();

    }
}
