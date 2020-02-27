package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.Cloud;
import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;

import java.util.UUID;

/**
 * This model provides users a particular
 * access to create or update a Migration
 */
public class MigrationBind {
    /**
     * Source Workload Id
     *
     * @see Workload
     */
    private UUID sourceId;
    /**
     * Target Workload Id
     *
     * @see Workload
     */
    private UUID targetId;
    /**
     * Target cloud type
     *
     * @see Cloud
     */
    private Cloud targetCloud;
    /**
     * Target cloud credentials
     */
    private Credentials cloudCredentials;
    /**
     * List of mount point to conversion
     * Required the main mount point
     */
    private String[] mountPoints;
    /**
     * OS type
     */
    private OsType osType;
    /**
     * Migration state
     */
    private State state;

    /**
     * Constructor
     */
    public MigrationBind() {

    }

    /**
     * Source Id getter
     *
     * @return UUID of source
     */
    public UUID getSourceId() {
        return sourceId;
    }

    /**
     * Target Id getter
     *
     * @return target UUID
     */
    public UUID getTargetId() {
        return targetId;
    }

    /**
     * Target cloud getter
     *
     * @return Target cloud
     */
    public Cloud getTargetCloud() {
        return targetCloud;
    }

    /**
     * Cloud credentials Getter
     *
     * @return Credentials
     * @see Credentials
     */
    public Credentials getCloudCredentials() {
        return cloudCredentials;
    }

    /**
     * Mount point getter
     *
     * @return Array of mount points
     */
    public String[] getMountPoints() {
        return mountPoints;
    }

    /**
     * Os type getter
     *
     * @return OsType
     * @see OsType
     */
    public OsType getOsType() {
        return osType;
    }

    /**
     * Migration state getter
     *
     * @return Migration state
     */
    public State getState() {
        return state;
    }

    /**
     * Migration state setter
     *
     * @param state Migration state
     */
    public void setState(State state) {
        this.state = state;
    }
}
