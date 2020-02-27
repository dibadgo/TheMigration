package com.example.dibadgo.TheMigration.domain;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * This model describes a volume
 */
@UserDefinedType("volume")
public class Volume {

    /**
     * Mount point (C:, /)
     */
    private String mountPoint;

    /**
     * Total size of a volume (GB)
     */
    private int totalSize;

    /**
     * Default constructor
     */
    public Volume() {
    }

    /**
     * Custom constructor
     *
     * @param mountPoint Mount point
     * @param totalSize  Total size (Gigabytes)
     */
    public Volume(String mountPoint, int totalSize) {
        this.mountPoint = mountPoint;
        this.totalSize = totalSize;
    }

    /**
     * Mount point of a volume getter
     *
     * @return Mount point
     */
    public String getMountPoint() {
        return mountPoint;
    }

    /**
     * Mount volume setter
     *
     * @param mountPoint Mount volume
     */
    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    /**
     * Total size getter
     *
     * @return total size
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Make a clone of the current Volume
     *
     * @return Clone of that volume
     */
    public Volume cloneVolume() {
        return new Volume(mountPoint, totalSize);
    }
}
