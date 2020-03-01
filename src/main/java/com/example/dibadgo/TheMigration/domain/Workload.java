package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Workload model
 * Cassandra table "workloads"
 */
@Table("workloads")
public class Workload {

    /**
     * Workload Id
     * Primary key
     * Will store in Cassandra
     */
    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    /**
     * IP address
     * Will store in Cassandra
     */
    @CassandraType(type = DataType.Name.TEXT)
    private String ipAddress;

    /**
     * Credentials (User name, password etc...)
     * Will store in Cassandra
     *
     * @see Credentials
     */
    @CassandraType(type = DataType.Name.UDT, userTypeName = "credentials")
    private Credentials credentials;

    /**
     * Storage (List of Volumes)
     * Will store in Cassandra
     *
     * @see Volume
     */
    @CassandraType(type = DataType.Name.UDT, userTypeName = "volume")
    private List<Volume> volumeList;

    /**
     * Constructor
     */
    public Workload() {
    }

    /**
     * Constructor
     *
     * @param ipAddress   IP address
     * @param credentials Credentials
     */
    public Workload(String ipAddress, Credentials credentials) {
        this.ipAddress = ipAddress;
        this.credentials = credentials;
        this.volumeList = new ArrayList<>();
    }

    @NotNull
    public UUID getId() {
        return id;
    }

    public void setId(@NotNull UUID id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public List<Volume> getVolumeList() {
        return volumeList;
    }

    /**
     * Add volume to storage
     *
     * @param volume Volume to add
     * @throws WorkloadException Throws, if mount point already exists on the volume
     * @see Volume
     */
    public void addVolume(@NotNull Volume volume) throws WorkloadException {
        if (this.volumeList == null) {
            this.volumeList = new ArrayList<>();
        }

        if (this.getVolumeByMountPoint(volume.getMountPoint()) == null)
            this.volumeList.add(volume);
        else {
            String errorMessage = String.format(
                    "The volume with mount point %s is already exists in Workload %s",
                    volume.getMountPoint(),
                    this.getId().toString()
            );
            throw new WorkloadException(errorMessage);
        }
    }

    /**
     * Search volume in storage which will match with mount point
     *
     * @param mountPoint Mount volume
     * @return Return Volume or Null
     * @see Volume
     */
    @Nullable
    public Volume getVolumeByMountPoint(@NotNull String mountPoint) {
        for (Volume volume : volumeList) {
            if (volume.getMountPoint().toLowerCase().equals(mountPoint)) {
                return volume;
            }
        }
        return null;
    }
}
