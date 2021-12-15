package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
@Getter
public enum Role {
    USER(Set.of(Permission.USER)),
    ADMIN(Set.of(Permission.ADMIN, Permission.USER)),
    MODERATOR(Set.of(Permission.MODERATOR, Permission.USER));

    private final Set<Permission> permissionSet;

    Role(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public Set<SimpleGrantedAuthority> getAuthorites() {
        return getPermissionSet().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
