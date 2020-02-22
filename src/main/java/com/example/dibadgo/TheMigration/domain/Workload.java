package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

@Table("workloads")
public class Workload {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;
    private IpAddress ipAddress;
    private Credentials credentials;
    private ArrayList<Volume> volumeList;

    public Workload() {
    }

    public Workload(IpAddress ipAddress, Credentials credentials, ArrayList<Volume> volumeList) {
        this.ipAddress = ipAddress;
        this.credentials = credentials;
        this.volumeList = volumeList;
    }

    public Workload(UUID id, IpAddress ipAddress, Credentials credentials, ArrayList<Volume> volumeList) {
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

    public IpAddress getIpAddress() {
        return ipAddress;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public ArrayList<Volume> getVolumeList() {
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
