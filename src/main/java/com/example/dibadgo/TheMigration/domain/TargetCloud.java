package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import com.example.dibadgo.TheMigration.base.Cloud;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

/**
 * Target cloud is a model describes a settings of a target workload
 */
@UserDefinedType("targetcloud")
public class TargetCloud {

    /**
     * Target cloud type
     *
     * @see Cloud
     */
    @CassandraType(type = DataType.Name.TEXT)
    private Cloud targetCloud;

    /**
     * Target cloud credentials
     *
     * @see Credentials
     */
    @CassandraType(type = DataType.Name.UDT, userTypeName = "credentials")
    private Credentials cloudCredentials;

    /**
     * Target Workload Id
     */
    @CassandraType(type = DataType.Name.UUID)
    private UUID targetId;

    /**
     * Target workload
     *
     * @see Workload
     */
    @Transient
    private Workload target;

    /**
     * @param targetCloud      Target cloud type
     * @param cloudCredentials Target cloud credentials
     * @param targetId         Target Workload Id
     */
    public TargetCloud(Cloud targetCloud, Credentials cloudCredentials, UUID targetId) {
        this.targetCloud = targetCloud;
        this.cloudCredentials = cloudCredentials;
        this.targetId = targetId;
    }

    /**
     * Target cloud getter
     *
     * @return Cloud
     */
    public Cloud getTargetCloud() {
        return targetCloud;
    }

    /**
     * Cloud credentials getter
     *
     * @return Credentials
     */
    public Credentials getCloudCredentials() {
        return cloudCredentials;
    }

    /**
     * Target Workload id getter
     *
     * @return UUID
     */
    public UUID getTargetId() {
        return targetId;
    }

    /**
     * Taget Workload getter
     *
     * @return Workload
     */
    public Workload getTarget() {
        return target;
    }

    /**
     * Target Workload setter
     *
     * @param target Workload
     */
    public void setTarget(Workload target) {
        this.target = target;
    }
}
