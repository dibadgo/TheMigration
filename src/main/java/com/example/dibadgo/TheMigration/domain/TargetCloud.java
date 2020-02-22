package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.Cloud;

public class TargetCloud {

    private Cloud targetCloud;

    private Credentials cloudCredentials;

    private Workload target;

    public TargetCloud(Cloud targetCloud, Credentials cloudCredentials, Workload target) {
        this.targetCloud = targetCloud;
        this.cloudCredentials = cloudCredentials;
        this.target = target;
    }

    public Cloud getTargetCloud() {
        return targetCloud;
    }

    public Credentials getCloudCredentials() {
        return cloudCredentials;
    }

    public Workload getTarget() {
        return target;
    }
}
