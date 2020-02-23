package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.Cloud;
import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;

import java.util.UUID;

public class MigrationBind {
    private UUID sourceId;
    private UUID targetId;
    private Cloud targetCloud;
    private Credentials cloudCredentials;
    private String[] mountPoints;
    private OsType osType;
    private State state;

    public MigrationBind() {

    }

    public UUID getSourceId() {
        return sourceId;
    }

    public UUID getTargetId() {
        return targetId;
    }

    public Cloud getTargetCloud() {
        return targetCloud;
    }

    public Credentials getCloudCredentials() {
        return cloudCredentials;
    }

    public String[] getMountPoints() {
        return mountPoints;
    }

    public OsType getOsType() {
        return osType;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
