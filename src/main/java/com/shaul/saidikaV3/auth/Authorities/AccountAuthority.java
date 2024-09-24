package com.shaul.saidikaV3.auth.Authorities;

import org.springframework.security.core.GrantedAuthority;

public class AccountAuthority implements GrantedAuthority {

    private String authority;

    public AccountAuthority(String authority)
    {
        this.authority=authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode()==obj.hashCode();
    }

    @Override
    public int hashCode() {
        return authority.hashCode();
    }
}
