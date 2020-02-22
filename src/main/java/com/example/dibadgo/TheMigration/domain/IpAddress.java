package com.example.dibadgo.TheMigration.domain;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

@UserDefinedType(value = "IpV4Address")
public class IpAddress {

    private static final String REG_EX = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";

    private String ipAddress;

    public IpAddress() {
    }

    public IpAddress(@NotNull String ipAddress) throws RuntimeException {
        if (!IpAddress.checkIpAddress(ipAddress)) {
            throw new RuntimeException("IP v4 should match with template 000.000.000.000");
        }

        this.ipAddress = ipAddress;
    }

    public static Boolean checkIpAddress(@NotNull String ipAddress) {
        Pattern pattern = Pattern.compile(REG_EX);
        return pattern.matcher(ipAddress).matches();
    }

    @NotNull
    public String getIpAddress() {
        return ipAddress;
    }
}
