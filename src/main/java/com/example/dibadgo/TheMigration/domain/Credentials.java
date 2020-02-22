package com.example.dibadgo.TheMigration.domain;

import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.validation.constraints.NotNull;

@UserDefinedType("credentials")
public class Credentials {

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
    public Credentials(@NotNull String password, @NotNull String username, String domain) {
        this.password = password;
        this.username = username;
        this.domain = domain;
    }

    /**
     * @return
     */
    public String getUseName() {
        return username;
    }

    /**
     * Sets the Username
     *
     * @param userName Username should not be null
     */

    public void setUseName(@NotNull String userName) {
        this.username = userName;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
