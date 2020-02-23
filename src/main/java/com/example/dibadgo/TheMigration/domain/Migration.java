package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.base.StatefulModel;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Table("migrations")
public class Migration extends StatefulModel {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    @CassandraType(type = DataType.Name.TEXT)
    private List<String> selectedMountPoints;

    @CassandraType(type = DataType.Name.UUID)
    private UUID sourceId;

    @Transient
    private Workload source;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "targetcloud")
    private TargetCloud targetCloud;

    private State state;

    private OsType osType;

    public Migration() {

    }

    public Migration(String[] selectedMountPoints,
                     UUID sourceId,
                     TargetCloud targetCloud,
                     State state,
                     OsType osType) {

        this.id = null;
        this.selectedMountPoints = new ArrayList<>(Arrays.asList(selectedMountPoints));
        this.sourceId = sourceId;
        this.targetCloud = targetCloud;
        this.state = state;
        this.osType = osType;
    }

    public void run(int TIME_SLEEP_MIN) throws LocalMigrationError, InterruptedException {
        // Make sure that main mount point is selected
        String mainMountPoint = osType.getMountPoint();
        if (!getSelectedMountPoints().contains(mainMountPoint)) {
            throw new LocalMigrationError("You cannot start the migration without main mount point");
        }

        cloneMountPoints();

        Thread.sleep(TIME_SLEEP_MIN * 60 * 1000);
    }

    private void cloneMountPoints() throws LocalMigrationError {
        Workload target = this.targetCloud.getTarget();
        for (String mountPoint : getSelectedMountPoints()) {
            Volume sourceVolume = source.getVolumeByMountPoint(mountPoint);
            if (sourceVolume == null) {
                throw new LocalMigrationError(String.format("Cannot find source mount point %s", mountPoint));
            }

            // Emulate block-based copy for example
            target.addVolume(sourceVolume.cloneVolume());
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TargetCloud getTargetCloud() {
        return targetCloud;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public void setSource(Workload source) {
        this.source = source;
    }

    public Workload getSource() {
        return source;
    }

    public List<String> getSelectedMountPoints() {
        return selectedMountPoints.stream().map(String::toLowerCase).collect(Collectors.toList());
    }
}
