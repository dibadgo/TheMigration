package com.example.dibadgo.TheMigration.model;

import javax.validation.constraints.NotNull;

public class CloudCredentials implements Credentials {

    /**
     * Field for account password
     */
    private String password;

    /**
     * Filed for User name
     */
    private String username;

    /**
     * Field for domain
     */
    private String domain;

    /**
     * @param password
     * @param username
     * @param domain
     */
    public CloudCredentials(@NotNull String password, @NotNull String username, String domain) {
        this.password = password;
        this.username = username;
        this.domain = domain;
    }

    /**
     * @return
     */
    @Override
    public String getUseName() {
        return username;
    }

    /**
     * Sets the Username
     *
     * @param userName Username should not be null
     */
    @Override
    public void setUseName(@NotNull String userName) {
        this.username = userName;
    }

    /**
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public void setDomain(String domain) {
        this.domain = domain;
    }
}
