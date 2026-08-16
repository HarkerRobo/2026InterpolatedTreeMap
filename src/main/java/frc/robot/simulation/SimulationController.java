package frc.robot.simulation;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Milliseconds;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Seconds;

import java.nio.file.attribute.DosFileAttributeView;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;
import frc.robot.util.MathUtil;

public class SimulationController
{
    private static SimulationController instance;

    private boolean simulatingBall = false;

    // this could be done more concisely but far less memory efficiently through Translation3ds
    private double positionX;
    private double positionY;
    private double positionZ;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private final double accelerationZ = Constants.Simulation.BALL_MASS * Constants.Simulation.G;

    private int fuelsScored = 0;
    private int fuelsMissed = 0;

    private double lastTime;

    private SimulationController ()
    {
        lastTime = Milliseconds.of(System.currentTimeMillis()).in(Seconds);
    }


    public Translation3d getBallPosition()
    {
        if (simulatingBall)
            return new Translation3d(positionX, positionY, positionZ);
        else return new Translation3d(-1.0, -1.0, -1.0);
    }

    private void stepBall(double timeElapsed)
    {
        velocityZ += accelerationZ * timeElapsed;

        positionX += velocityX * timeElapsed;
        positionY += velocityY * timeElapsed;
        positionZ += velocityZ * timeElapsed;
    }

    public void addBall()
    {
        simulatingBall = true;

        Pose2d drivetrainPose = Drivetrain.getInstance().getPose();

        positionX = drivetrainPose.getX();
        positionY = drivetrainPose.getY();
        positionZ = Constants.Simulation.SHOOTING_HEIGHT_FROM_GROUND;

        double shotVelocity = Shooter.getInstance().getVelocity().in(MetersPerSecond);
        Rotation3d shotDirection = new Rotation3d(0.0, Hood.getInstance().getAngle().in(Rotations), drivetrainPose.getRotation().getRotations());
        Translation3d shotVector = new Translation3d(shotVelocity, shotDirection);

        velocityX = shotVector.getX();
        velocityY = shotVector.getY();
        velocityZ = shotVector.getZ();
    }

    public void periodic()
    {
        double currentTime = Milliseconds.of(System.currentTimeMillis()).in(Seconds);
        double elapsedTime = currentTime - lastTime;
        lastTime = currentTime;

        if (simulatingBall)
        {
            stepBall(elapsedTime);

            if (MathUtil.inRectifiedRectangle2d(positionX, positionY, Constants.Simulation.HUB_CONTENTS))
            {
                simulatingBall = false;
                if (Math.abs(positionZ - Constants.Simulation.HUB_INTAKE_HEIGHT) < Constants.Simulation.FUEL_DIAMETER / 2.0) // fuel lands in the hub
                {
                    fuelsScored += 1;
                }
                else // fuel undershoots and hits the hub
                {
                    fuelsMissed += 1;
                }
            }
            else if (positionZ < Constants.Simulation.FUEL_DIAMETER / 2.0) // fuel hits the ground
            {
                fuelsMissed += 1;
            }
        }
    }

    public int getFuelsScored()
    {
        return fuelsScored;
    }

    public int getFuelsMissed()
    {
        return fuelsMissed;
    }

    public static SimulationController getInstance()
    {
        if (instance == null) instance = new SimulationController();
        return instance;
    }
}
