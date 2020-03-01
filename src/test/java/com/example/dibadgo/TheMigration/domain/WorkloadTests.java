package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.domain.Credentials;
import com.example.dibadgo.TheMigration.domain.Volume;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkloadTests {

    @Test
    public void sameMountPoint() {
        Assertions.assertThrows(WorkloadException.class, () -> {
            Workload workload = generateWorkload();
            workload.addVolume(new Volume("c:", 12));
        });
    }

    @Test
    public void getVolumeByMountPoint() {
        Workload workload = generateWorkload();
        Volume volume = workload.getVolumeByMountPoint("c:");

        assert volume != null;
        assertEquals(volume.getMountPoint(), "c:");
    }

    private Workload generateWorkload() {
        Credentials credentials = new Credentials("passwd", "username", "");
        Workload workload = new Workload("192.168.0.1", credentials);
        workload.addVolume(new Volume("c:", 12));
        return workload;
    }
}
