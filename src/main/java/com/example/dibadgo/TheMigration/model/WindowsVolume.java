package com.example.dibadgo.TheMigration.model;

import com.example.dibadgo.TheMigration.base.Volume;

public class WindowsVolume implements Volume {

    /**
     * The main mount point for Windows.
     * If that point is not allowed - the migration cannot start
     */
    public static final String MAIN_MOUNT_POINT = "C:\\";

    private String mountPoint;

    private int totalSize;

    public WindowsVolume(String mountPoint, int totalSize) {
        this.mountPoint = mountPoint;
        this.totalSize = totalSize;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public Volume clone() throws CloneNotSupportedException {
        return (Volume) super.clone();
    }
}
