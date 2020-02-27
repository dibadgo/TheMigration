package com.example.dibadgo.TheMigration.base;

import javax.validation.constraints.NotNull;

/**
 * Type of OS
 */
public enum OsType {
    WINDOWS("C:"),
    LINUX("/"),
    MAC("/");

    private String mountPoint;

    /**
     * Constructor
     *
     * @param mountPoint Main mount point
     */
    OsType(@NotNull String mountPoint) {
        this.mountPoint = mountPoint;
    }

    /**
     * Getter for main mount point
     * @return Main mount point (C:, /)
     */
    @NotNull
    public String getMountPoint() {
        return mountPoint.toLowerCase();
    }
}
