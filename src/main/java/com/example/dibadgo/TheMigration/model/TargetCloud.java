package com.example.dibadgo.TheMigration.model;

import com.example.dibadgo.TheMigration.base.Cloud;

public class TargetCloud {

    private Cloud targetCloud;

    private CloudCredentials cloudCredentials;

    private Workload target;

    public TargetCloud(Cloud targetCloud, CloudCredentials cloudCredentials, Workload target) {
        this.targetCloud = targetCloud;
        this.cloudCredentials = cloudCredentials;
        this.target = target;
    }

    public Cloud getTargetCloud() {
        return targetCloud;
    }

    public CloudCredentials getCloudCredentials() {
        return cloudCredentials;
    }

    public Workload getTarget() {
        return target;
    }
}
