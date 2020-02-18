package com.example.dibadgo.TheMigration.model;

import com.example.dibadgo.TheMigration.base.Credentials;
import com.example.dibadgo.TheMigration.base.IpAddress;
import com.example.dibadgo.TheMigration.base.Volume;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class Workload {

    private IpAddress ipAddress;
    private Credentials credentials;
    private ArrayList<Volume> volumeList;

    public Workload(IpAddress ipAddress, Credentials credentials, ArrayList<Volume> volumeList) {
        this.ipAddress = ipAddress;
        this.credentials = credentials;
        this.volumeList = volumeList;
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

    public void addVolume(@NotNull Volume volume){
        this.volumeList.add(volume);
    }

    @Nullable
    public Volume getVolumeByMountPoint(@NotNull String mountPoint) {
        for (Volume volume: volumeList) {
            if (volume.getMountPoint().equals(mountPoint)) {
                return volume;
            }
        }
        return null;
    }
}
