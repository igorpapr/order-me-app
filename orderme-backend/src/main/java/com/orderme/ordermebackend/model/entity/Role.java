package com.orderme.ordermebackend.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    SUPER_ADMIN(0),
    ADMIN(1),
    USER(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role getRoleById(Integer id) {
        if (id == null) {
            return null;
        }
        for (Role role: Role.values()) {
            if (id.equals(role.getId())) {
                return role;
            }
        }
        throw new IllegalArgumentException("No matching type for id: " + id);
    }

    public int getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
