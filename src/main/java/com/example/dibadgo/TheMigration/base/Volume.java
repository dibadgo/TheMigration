package com.example.dibadgo.TheMigration.base;

public interface Volume {
    String getMountPoint();

    void setMountPoint(String mountPoint);

    int getTotalSize();

    void setTotalSize(int totalSize);

    Volume clone() throws CloneNotSupportedException;
}
