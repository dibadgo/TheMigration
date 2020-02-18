package com.example.dibadgo.TheMigration.model;

import com.example.dibadgo.TheMigration.base.IpAddress;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

public class IpV4Address implements IpAddress {

    private static final String REG_EX = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";

    private String ipAddress;

    public IpV4Address(@NotNull String ipAddress) throws Exception {
        if (!IpV4Address.checkIpAddress(ipAddress)) {
            throw new Exception("IP v4 should match with template 000.000.000.000");
        }

        this.ipAddress = ipAddress;
    }

    public static Boolean checkIpAddress(@NotNull String ipAddress) {
        Pattern pattern = Pattern.compile(REG_EX);
        return pattern.matcher(ipAddress).matches();
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }
}
