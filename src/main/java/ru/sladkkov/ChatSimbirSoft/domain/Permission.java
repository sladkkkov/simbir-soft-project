package ru.sladkkov.ChatSimbirSoft.domain;

public enum Permission {
  USER("USER"),
  ADMIN("ADMIN"),
  MODERATOR("MODERATOR");
private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
