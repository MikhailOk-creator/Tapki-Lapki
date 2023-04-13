package ru.mirea.tapki_lapki.business_object.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, MANAGER, ADMIN, SUPER_ADMIN;
    @Override
    public String getAuthority() {
        return null;
    }
}
