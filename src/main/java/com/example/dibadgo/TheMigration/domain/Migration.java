package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public class Migration {

    private UUID id;
    private ArrayList<String> selectedMountPoints;
    private Workload source;
    private TargetCloud targetCloud;
    private State state;
    private OsType osType;

    public Migration() {
    }

    public Migration(UUID id,
                     String[] selectedMountPoints,
                     Workload source,
                     TargetCloud targetCloud,
                     State state,
                     OsType osType) {

        this.id = id;
        this.selectedMountPoints = new ArrayList<>(Arrays.asList(selectedMountPoints));
        this.source = source;
        this.targetCloud = targetCloud;
        this.state = state;
        this.osType = osType;
    }

    void run() throws LocalMigrationError {
        // Set the migration status to RUNNING
        this.state = State.RUNNING;

        // Make sure that main mount point is selected
        String mainMountPoint = this.osType.getMountPoint();
        if (!selectedMountPoints.contains(mainMountPoint)) {
            this.state = State.ERROR;
            throw new LocalMigrationError("You cannot start the migration without main mount point");
        }

        cloneMountPoints();
    }

    private void cloneMountPoints() throws LocalMigrationError {
        Workload target = this.targetCloud.getTarget();
        for (String mountPoint : this.selectedMountPoints) {
            Volume sourceVolume = this.source.getVolumeByMountPoint(mountPoint);
            if (sourceVolume == null) {
                throw new LocalMigrationError(String.format("Cannot find source mount point %s", mountPoint));
            }

            // Emulate block-based copy for example
            try {
                target.addVolume(sourceVolume.clone());
            } catch (CloneNotSupportedException cloneException) {
                throw new LocalMigrationError(
                        String.format("Cannot copy the source mount point %s", mountPoint),
                        cloneException
                );
            }
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
