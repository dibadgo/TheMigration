package com.example.dibadgo.TheMigration.model;

public interface Credentials {

    String getUseName();
    void setUseName(String userName);

    String getPassword();
    void setPassword(String password);

    String getDomain();
    void setDomain(String domain);
}