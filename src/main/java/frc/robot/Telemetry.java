package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import frc.robot.simulation.SimulationController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;

public class Telemetry 
{
    private static NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private static NetworkTable telemetryTable = instance.getTable("Telemetry Data");

    private static NetworkTable drivetrainTable = telemetryTable.getSubTable("Drivetrain");
    private static StructPublisher<Pose2d> pose = drivetrainTable.getStructTopic("Pose", Pose2d.struct).publish();
    private static DoublePublisher voltage = drivetrainTable.getDoubleTopic("Voltage").publish();
    private static DoublePublisher velocity = drivetrainTable.getDoubleTopic("Velocity").publish();
    private static DoublePublisher angularVoltage = drivetrainTable.getDoubleTopic("Angular Voltage").publish();
    private static DoublePublisher angularVelocity = drivetrainTable.getDoubleTopic("Angular Velocity").publish();
    private static DoublePublisher time = drivetrainTable.getDoubleTopic("Time").publish();

    private static DoublePublisher hoodAngle = telemetryTable.getDoubleTopic("Hood Angle (°)").publish();
    private static DoublePublisher shooterVelocity = telemetryTable.getDoubleTopic("Shooter Velocity (°)").publish();
    private static StructPublisher<Pose3d> fuelPose = telemetryTable.getStructTopic("Fuel Pose", Pose3d.struct).publish();
    private static IntegerPublisher fuelsScored = telemetryTable.getIntegerTopic("Fuels Scored").publish();
    private static IntegerPublisher fuelsMissed = telemetryTable.getIntegerTopic("Fuels Missed").publish();

    public static void init()
    {
    }

    public static NetworkTable getTable() 
    {
        return telemetryTable;
    }

    public static void update() 
    {
        pose.set(Drivetrain.getInstance().getPose());
        voltage.set(Drivetrain.getInstance().getVoltage());
        velocity.set(Drivetrain.getInstance().getVelocity());
        angularVoltage.set(Drivetrain.getInstance().getAngularVoltage());
        angularVelocity.set(Drivetrain.getInstance().getAngularVelocity());
        time.set(Drivetrain.getInstance().getTime());

        hoodAngle.set(Hood.getInstance().getAngle().in(Degrees));
        shooterVelocity.set(Shooter.getInstance().getVelocity().in(MetersPerSecond));

        fuelPose.set(new Pose3d(SimulationController.getInstance().getBallPosition(), new Rotation3d()));

        fuelsScored.set(SimulationController.getInstance().getFuelsScored());
        fuelsMissed.set(SimulationController.getInstance().getFuelsMissed());
    }
}
