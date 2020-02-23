package com.example.dibadgo.TheMigration.base;

import javax.validation.constraints.NotNull;

public enum OsType {
    WINDOWS("C:"),
    LINUX("/"),
    MAC("/");

    private String mountPoint;

    OsType(@NotNull String mountPoint) {
        this.mountPoint = mountPoint;
    }

    @NotNull
    public String getMountPoint() {
        return mountPoint.toLowerCase();
    }
}
