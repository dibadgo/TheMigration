package com.example.dibadgo.TheMigration.domain;

import com.datastax.driver.core.DataType;
import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Migration model
 * Casandra table 'migrations'
 */
@Table("migrations")
public class Migration {

    /**
     * Id of the migration
     * Will save in Cassandra like a primary key
     */
    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    /**
     * Selected mount points for migration
     * For Windows: c:, d:, e:,... etc..
     * For linux: sda1, sdb, sdc1,... etc
     * <p>
     * Will save in Cassandra
     */
    @CassandraType(type = DataType.Name.TEXT)
    private List<String> selectedMountPoints;

    /**
     * Source workload Id
     *
     * <p>
     *
     * @see Workload
     * Will save in Cassandra
     */
    @CassandraType(type = DataType.Name.UUID)
    private UUID sourceId;

    /**
     * Source workload
     * Obtains from cassandra table "workloads"
     * <p>
     * Will NOT save in Cassandra
     */
    @Transient
    private Workload source;

    /**
     *
     */
    @CassandraType(type = DataType.Name.UDT, userTypeName = "targetcloud")
    private TargetCloud targetCloud;

    /**
     *
     */
    @CassandraType(type = DataType.Name.TEXT)
    private State state;

    /**
     * Type of OS (Windows, Linux, Mac etc...)
     *
     * @see OsType
     */
    @CassandraType(type = DataType.Name.TEXT)
    private OsType osType;

    /**
     * Start time
     */
    @CassandraType(type = DataType.Name.TIMESTAMP)
    protected Date startTime;

    /**
     * Finish time
     */
    @CassandraType(type = DataType.Name.TIMESTAMP)
    protected Date endTime;

    /**
     * Error message (If state is error)
     */

    @CassandraType(type = DataType.Name.TEXT)
    protected String errorMessage;


    /**
     * Def constructor
     */
    public Migration() {

    }

    /**
     * @param selectedMountPoints Array of selected mount points
     * @param sourceId            Source Workload Id
     * @param targetCloud         Target cloud model
     * @param state               migration state
     * @param osType              OS type
     */
    public Migration(@NotNull String[] selectedMountPoints,
                     @NotNull UUID sourceId,
                     @NotNull TargetCloud targetCloud,
                     @NotNull State state,
                     @NotNull OsType osType) {

        this.id = null;
        this.selectedMountPoints = new ArrayList<>(Arrays.asList(selectedMountPoints));
        this.sourceId = sourceId;
        this.targetCloud = targetCloud;
        this.state = state;
        this.osType = osType;
    }

    /**
     * Runs the migration process
     * <p>
     * Will emulate the migration process.
     * Takes the selected volumes from source, copy that to target
     * and delay (sleep) the process then.
     *
     * @param minutesToSleep Minutes to delay
     * @throws LocalMigrationError Errors during migration process
     */
    public void run(int minutesToSleep) throws LocalMigrationError {
        // Make sure that main mount point is selected
        String mainMountPoint = osType.getMountPoint();
        if (!getSelectedMountPoints().contains(mainMountPoint)) {
            throw new LocalMigrationError("You cannot start the migration without main mount point");
        }

        cloneMountPoints();

        // Delay the migration
        try {
            Thread.sleep(minutesToSleep * 60 * 1000);
        } catch (InterruptedException interruptedException) {
            throw new LocalMigrationError("Cannot delay the migration", interruptedException);
        }
    }

    /**
     * Clones the selected mount points from source to target.
     * Emulates block-based copy for example
     *
     * @throws LocalMigrationError Error during cloning of mount points
     */
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

    public void setId(@NotNull UUID id) {
        this.id = id;
    }

    public TargetCloud getTargetCloud() {
        return targetCloud;
    }

    public State getState() {
        return state;
    }

    public void setState(@NotNull State state) {
        this.state = state;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public void setSource(@NotNull Workload source) {
        this.source = source;
    }

    public Workload getSource() {
        return source;
    }

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

    /**
     * Get selected mount points (lowecased)
     *
     * @return List of selected mount points
     */
    public List<String> getSelectedMountPoints() {
        return selectedMountPoints.stream().map(String::toLowerCase).collect(Collectors.toList());
    }
}
