package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import com.example.dibadgo.TheMigration.base.Cloud;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@UserDefinedType("targetcloud")
public class TargetCloud {

    private Cloud targetCloud;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "credentials")
    private Credentials cloudCredentials;

    @CassandraType(type = DataType.Name.UUID)
    private UUID targetId;

    @Transient
    private Workload target;

    public TargetCloud(Cloud targetCloud, Credentials cloudCredentials, UUID targetId) {
        this.targetCloud = targetCloud;
        this.cloudCredentials = cloudCredentials;
        this.targetId = targetId;
    }

    public Cloud getTargetCloud() {
        return targetCloud;
    }

    public Credentials getCloudCredentials() {
        return cloudCredentials;
    }

    public UUID getTargetId() {
        return targetId;
    }

    public Workload getTarget() {
        return target;
    }

    public void setTarget(Workload target) {
        this.target = target;
    }
}
