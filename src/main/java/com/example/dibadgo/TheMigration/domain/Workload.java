package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("workloads")
public class Workload {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    @CassandraType(type = DataType.Name.TEXT)
    private String ipAddress;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "credentials")
    private Credentials credentials;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "volume")
    private List<Volume> volumeList;

    public Workload() {
    }

    public Workload(String ipAddress, Credentials credentials, ArrayList<Volume> volumeList) {
        this.ipAddress = ipAddress;
        this.credentials = credentials;
        this.volumeList = volumeList;
    }

    public Workload(UUID id, String ipAddress, Credentials credentials, ArrayList<Volume> volumeList) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.credentials = credentials;
        this.volumeList = volumeList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public void addVolume(@NotNull Volume volume) {
        this.volumeList.add(volume);
    }

    @Nullable
    public Volume getVolumeByMountPoint(@NotNull String mountPoint) {
        for (Volume volume : volumeList) {
            if (volume.getMountPoint().equals(mountPoint)) {
                return volume;
            }
        }
        return null;
    }
}
