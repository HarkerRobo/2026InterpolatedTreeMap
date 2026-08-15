package frc.robot;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import frc.robot.subsystems.Drivetrain;

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
    private static DoublePublisher xPos = drivetrainTable.getDoubleTopic("xPos").publish();
    
    public static void init ()
    {
    }

    public static NetworkTable getTable ()
    {
        return telemetryTable;
    }

    public static void update ()
    {
        pose.set(Drivetrain.getInstance().getPose());
        voltage.set(Drivetrain.getInstance().getVoltage());
        velocity.set(Drivetrain.getInstance().getVelocity());
        angularVoltage.set(Drivetrain.getInstance().getAngularVoltage());
        angularVelocity.set(Drivetrain.getInstance().getAngularVelocity());
        time.set(Drivetrain.getInstance().getTime());
    }
}
