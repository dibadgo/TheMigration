package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.Cloud;
import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MigrationsTests {

    private static final int TIME_SLEEP_MINUTES = 1;

    @Test
    public void runMethodShouldCopySelectedMountPoints() {
        Workload source = generateWorkload("c:", "d:", "e:");
        Workload target = generateWorkload();

        Credentials credentials = generateCredentials();

        String[] selected = new String[]{"c:", "e:"};

        TargetCloud targetCloud = new TargetCloud(Cloud.AWS, credentials, UUID.randomUUID());
        targetCloud.setTarget(target);

        Migration migration = new Migration(selected, UUID.randomUUID(), targetCloud, State.PENDING, OsType.WINDOWS);
        migration.setSource(source);

        migration.run(TIME_SLEEP_MINUTES);

        for (String s : selected) {
            Volume volume = target.getVolumeByMountPoint(s);
            assert volume != null;
        }
    }

    private Workload generateWorkload(String... mountPoints) {
        Credentials credentials = generateCredentials();
        Workload workload = new Workload("192.168.0.1", credentials);
        if (mountPoints != null) {
            for (String mountPoint : mountPoints) {
                workload.addVolume(new Volume(mountPoint, 12));
            }
        }
        return workload;
    }

    private Credentials generateCredentials() {
        return new Credentials("passwd", "username", "");
    }
}
