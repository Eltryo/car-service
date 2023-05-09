package com.example.carrestapi.security;

public enum ApplicationUserAuthority {
    CAR_READ("car:read"),
    CAR_WRITE("car:write");

    private final String permission;

    ApplicationUserAuthority(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
