package com.example.dibadgo.TheMigration.model;

public class Volume {
    private String mountPoint;

    private int totalSize;

    public Volume(String mountPoint, int totalSize) {
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
}
