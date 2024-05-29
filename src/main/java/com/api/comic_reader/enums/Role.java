package com.api.comic_reader.enums;

import org.springframework.security.core.GrantedAuthority;

// This enum represents the different roles a user can have in the application.
// It implements GrantedAuthority, which is a core part of Spring Security's access-control mechanism.
public enum Role implements GrantedAuthority {
    // The ADMIN role typically has all permissions.
    ADMIN,
    // The USER role has fewer permissions.
    USER;

    // This method returns the name of the role.
    // In Spring Security, this name is used as the authority that can be granted to a user.
    @Override
    public String getAuthority() {
        return name();
    }
}
