package com.example.dibadgo.TheMigration.model;

import java.util.List;

public class Workload {

    private IpAddress ipAddress;
    private Credentials credentials;
    private List<Volume> volumeList;

    public Workload(IpAddress ipAddress, Credentials credentials, List<Volume> volumeList) {
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

    public List<Volume> getVolumeList() {
        return volumeList;
    }

    public void setVolumeList(List<Volume> volumeList) {
        this.volumeList = volumeList;
    }
}
