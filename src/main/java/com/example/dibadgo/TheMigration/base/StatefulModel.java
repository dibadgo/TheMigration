package com.example.dibadgo.TheMigration.base;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.LocalDate;
import org.springframework.data.cassandra.core.mapping.CassandraType;

import java.util.Date;

public class StatefulModel {

    @CassandraType(type = DataType.Name.TIMESTAMP)
    protected Date startTime;

    @CassandraType(type = DataType.Name.TIMESTAMP)
    protected Date endTime;

    @CassandraType(type = DataType.Name.TEXT)
    protected String errorMessage;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
