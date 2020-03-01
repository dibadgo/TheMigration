package com.example.dibadgo.TheMigration.domain;


import com.example.dibadgo.TheMigration.domain.Volume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VolumeTests {

    @Test
    public void volumeCloneShouldNotBeEqualObject() {
        Volume volume = new Volume("/", 12);
        Volume clone = volume.cloneVolume();
        assertNotEquals(volume, clone);
        assertEquals(volume.getMountPoint(), clone.getMountPoint());
        assertEquals(volume.getTotalSize(), clone.getTotalSize());
    }

    @Test
    public void mountPointShouldBeLowercase() {
        String mountPoint = "C:";
        Volume volume = new Volume(mountPoint, 12);
        assertEquals(volume.getMountPoint(), mountPoint.toLowerCase());
    }
}
