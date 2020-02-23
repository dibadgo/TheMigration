package com.example.dibadgo.TheMigration.domain;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("volume")
public class Volume {

    private String mountPoint;

    private int totalSize;

    public Volume() {
    }

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


    public Volume cloneVolume() {
        return new Volume(mountPoint, totalSize);
    }
}
