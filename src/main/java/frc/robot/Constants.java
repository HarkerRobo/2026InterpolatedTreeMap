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

    public static class Hood
    {
        public static final Angle MINIMUM_ANGLE = Degrees.of(60.0);
        public static final Angle MAXIMUM_ANGLE = Degrees.of(75.0);

        public static final Angle INITIAL_ANGLE = Degrees.of(60.0);
    }

    public static class Shooter
    {
        public static final LinearVelocity MINIMUM_SPEED = MetersPerSecond.of(0.0);
        public static final LinearVelocity MAXIMUM_SPEED = MetersPerSecond.of(10.0);

        public static final LinearVelocity INITIAL_SPEED = MetersPerSecond.of(0.0);
    }

    public static class Simulation
    {
        public static final double FIELD_HEIGHT = 8.069326; // meters
        public static final double FIELD_WIDTH = 16.540988; // meters

        public static final Translation2d FIELD_CENTER = new Translation2d(FIELD_WIDTH / 2.0, FIELD_HEIGHT / 2.0);

        public static final Function<Double, Double> ROTATE_X = (Double x) -> x + 2 * (FIELD_CENTER.getX() - x);
        public static final Function<Double, Double> ROTATE_Y = (Double y) -> y + 2 * (FIELD_CENTER.getY() - y);

        public static final Translation2d CENTER_UPPER_REFERENCE = new Translation2d(FIELD_CENTER.getX(), FIELD_CENTER.getY() + 0.0254);
        public static final Translation2d CENTER_LOWER_REFERENCE = new Translation2d(FIELD_CENTER.getX(), FIELD_CENTER.getY() - 0.0254);

        public static final double FUEL_DIAMETER = 0.15; // meters
        public static final double MIN_FUEL_MASS = 0.203; // kg
        public static final double MAX_FUEL_MASS = 0.227; // kg

        public static final Rectangle2d HUB_CONTENTS = new Rectangle2d(new Pose2d(new Translation2d(4.574794, 4.059936), new Rotation2d()), 1.1938, 1.1938);

        public static final double BALL_MASS = (MAX_FUEL_MASS + MIN_FUEL_MASS) / 2.0;
        public static final double G = 9.81;

        public static final double HUB_INTAKE_HEIGHT = 1.8288; // meters
    
        public static final double SHOOTING_HEIGHT_FROM_GROUND = 0.5; // meters
    }
}
