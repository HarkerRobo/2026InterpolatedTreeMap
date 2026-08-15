package frc.robot.subsystems;


import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants;
import frc.robot.Telemetry;

public class Drivetrain implements Subsystem
{
    private static Drivetrain instance;
    Pose2d pose;
    double voltageApplied;
    double currentVelocity;
    double angularVoltageApplied;
    double angularCurrentVelocity;
    double currentTime;
    double xPos;

    private Drivetrain()
    {
        pose = new Pose2d();
        voltageApplied = 0;
        currentTime = System.currentTimeMillis()/1000.0;
    }

    public void rotate (double voltage)
    {
        angularVoltageApplied = voltage;
    }

    public void drive (double voltage)
    {
        voltageApplied = voltage;
    }

    @Override
    public void periodic ()
    {
        double newTime = System.currentTimeMillis()/1000.0;
        double deltaTime = newTime - currentTime;
        currentTime = newTime;


        pose = pose.plus(new Transform2d(new Translation2d(currentVelocity * deltaTime, 0), new Rotation2d()));

        xPos=pose.getX();
        /// POSITION
        // Note that V = Ks * sgn(v) + Kv * v + Ka * a

        double effectiveVoltage = voltageApplied - Constants.Drivetrain.Ks * (currentVelocity >= 0 ? 1 : -1);
        if (effectiveVoltage < 0 != voltageApplied < 0) effectiveVoltage = 0;

        double acceleratingVoltage = effectiveVoltage - currentVelocity * Constants.Drivetrain.Kv;

        double acceleration = acceleratingVoltage / Constants.Drivetrain.Ka;


        currentVelocity += acceleration * deltaTime;
       
        // Real-world constraints
        if (currentVelocity > 8) currentVelocity = 8;
        if (currentVelocity < -8) currentVelocity = -8;

        /// ROTATION
        // Note that V = Kva * v
        angularCurrentVelocity = angularVoltageApplied / Constants.Drivetrain.Kva;
        pose = new Pose2d(pose.getTranslation(),
                pose.getRotation().plus(new Rotation2d(angularCurrentVelocity * deltaTime)));
       
        { // boundary clipping
            double x = pose.getTranslation().getX();
            double y = pose.getTranslation().getY();
            double t = pose.getRotation().getRadians();
            double h = Constants.Drivetrain.HEIGHT;
            double m = h * Math.sqrt(2) / 2;
            double p = Math.PI / 4;
            double X0 = 0.0;
            double X1 = Constants.Simulation.FIELD_WIDTH;
            double Y0 = 0.0;
            double Y1 = Constants.Simulation.FIELD_HEIGHT;

            double xtl = x + m * Math.cos(p + t);
            double ytl = y + m * Math.sin(p + t);
            
            double xtr = x + m * Math.cos(p - t);
            double ytr = y + m * Math.sin(p - t);
            
            double xbl = x + m * Math.cos(p + 3 * t);
            double ybl = y + m * Math.sin(p + 3 * t);
            
            double xbr = x + m * Math.cos(p - 3 * t);
            double ybr = y + m * Math.sin(p - 3 * t);

            double dx = 0;
            double dy = 0;

            if (xtl + dx < X0) dx = X0 - xtl;
            if (xtl + dx > X1) dx = X1 - xtl;
            if (xtr + dx < X0) dx = X0 - xtr;
            if (xtr + dx > X1) dx = X1 - xtr;
            if (xbl + dx < X0) dx = X0 - xbl;
            if (xbl + dx > X1) dx = X1 - xbl;
            if (xbr + dx < X0) dx = X0 - xbr;
            if (xbr + dx > X1) dx = X1 - xbr;

            if (ytl + dy < Y0) dy = Y0 - ytl;
            if (ytl + dy > Y1) dy = Y1 - ytl;
            if (ytr + dy < Y0) dy = Y0 - ytr;
            if (ytr + dy > Y1) dy = Y1 - ytr;
            if (ybl + dy < Y0) dy = Y0 - ybl;
            if (ybl + dy > Y1) dy = Y1 - ybl;
            if (ybr + dy < Y0) dy = Y0 - ybr;
            if (ybr + dy > Y1) dy = Y1 - ybr;

            pose = new Pose2d(new Translation2d(x + dx, y + dy), pose.getRotation());
        }
    }
    
    public Pose2d getPose ()
    {
        return pose;
    }

    public double getVoltage ()
    {
        return voltageApplied;
    }

    public double getVelocity ()
    {
        return currentVelocity;
    }

    public double getAngularVoltage ()
    {
        return angularVoltageApplied;
    }

    public double getAngularVelocity ()
    {
        return angularCurrentVelocity;
    }

    public double getTime ()
    {
        return currentTime;
    }

    public static Drivetrain getInstance()
    {
        if (instance == null) instance = new Drivetrain();
        return instance;
    }
}
