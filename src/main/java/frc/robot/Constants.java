package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;

import java.util.function.Function;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rectangle2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;

public class Constants 
{
    public static class Drivetrain
    {
        public static final Pose2d ZERO_POSITION = new Pose2d(new Translation2d(0.5, 0.5), new Rotation2d());

        public static final double Ks = 1.0;
        public static final double Kv = 0.3;
        public static final double Ka = 0.1;

        public static final double Kva = 0.005;

        public static final double HEIGHT = 0.30;
    }

    public static class Simulation
    {
        public static final double FIELD_HEIGHT = 8.069326;//meters
        public static final double FIELD_WIDTH = 16.540988;//meters

        // excluding steel barrier
        public static final Rectangle2d DEPOT = new Rectangle2d(
            new Translation2d(0.0, FIELD_HEIGHT - 1.570736 - 0.0762),
            new Translation2d(0.6858 - 0.0762, FIELD_HEIGHT - 1.570736 - 1.0668 + 0.0762));
        
        public static final Translation2d FIELD_CENTER = new Translation2d(FIELD_WIDTH / 2.0, FIELD_HEIGHT / 2.0);

        public static final Function<Double, Double> ROTATE_X = (Double x) -> x + 2 * (FIELD_CENTER.getX() - x);
        public static final Function<Double, Double> ROTATE_Y = (Double y) -> y + 2 * (FIELD_CENTER.getY() - y);

        public static final Translation2d CENTER_UPPER_REFERENCE = new Translation2d(FIELD_CENTER.getX(), FIELD_CENTER.getY() + 0.0254);
        public static final Translation2d CENTER_LOWER_REFERENCE = new Translation2d(FIELD_CENTER.getX(), FIELD_CENTER.getY() - 0.0254);

        public static final double FUEL_DIAMETER = 0.15;//meters
        public static final double MIN_FUEL_MASS = 0.203;//kg
        public static final double MAX_FUEL_MASS = 0.227;//kg

        public static final int TOTAL_FUEL = 504;
        public static final int FUELS_TAKEN_BY_OTHER_ROBOTS = 0;

        public static final Rectangle2d HUB_CONTENTS = new Rectangle2d(new Pose2d(new Translation2d(4.574794, 4.059936), new Rotation2d()), 1.1938, 1.1938);

        public static final Rectangle2d OUTPOST = new Rectangle2d(
            new Translation2d(-0.84 - 0.5 * FUEL_DIAMETER, 0.331 - 0.5 * FUEL_DIAMETER), 
            new Translation2d(-0.0708 + 0.5 * FUEL_DIAMETER, 1.008 + 0.5 * FUEL_DIAMETER));

        public static final Translation3d OUTPOST_SPAWN_LOCATION_LOWER = new Translation3d(0.5 * FUEL_DIAMETER, 0.2655, 0.714);
        public static final Translation3d OUTPOST_SPAWN_LOCATION_UPPER = new Translation3d(0.5 * FUEL_DIAMETER, 1.0735, 0.714);

        public static final double BALL_MASS = (MAX_FUEL_MASS + MIN_FUEL_MASS) / 2.0;
        public static final double G = 9.81;

        public static final double HUB_INTAKE_HEIGHT = 1.8288;//meters
    }

    public static final Transform3d ROBOT_TO_HOOD = new Transform3d(0.3, 0.0, 0.34, Rotation3d.kZero); // TODO

    public static final Translation3d PASS_LEFT_TARGET_POSITION = new Translation3d(2.010664, 2.010664, 0.0);
    public static final Translation3d PASS_RIGHT_TARGET_POSITION = new Translation3d(2.010664, Simulation.FIELD_HEIGHT - 2.010664, 0.0);
    
    public static final Angle HARDCODE_HOOD_PITCH = Degrees.of(72.0);
    public static final double HARDCODE_VELOCITY = 7.35;

    public static final double PITCH_OFFSET_UNIT = 5.0;
    public static final double FLYWHEEL_OFFSET_UNIT = 0.5;

    public static final double DISTANCE_SHOOTVELO_RATIO = 0.7400067;
    public static final LinearVelocity SPEED_OFFSET = MetersPerSecond.of(0.275);

    public static final double ACCELERATION_LIMIT = 3.0;

    public static final double MID_PASS_VELOCITY = 12.7;
    public static final double MID_PASS_ANGLE = 65.0;
    
    public static final double HARD_PASS_VELOCITY = 18.0;
    public static final double HARD_PASS_ANGLE = 60.0; 
   
}
