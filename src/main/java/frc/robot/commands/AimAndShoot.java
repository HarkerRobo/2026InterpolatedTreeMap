package frc.robot.commands;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Telemetry;
import frc.robot.simulation.SimulationController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;

public class AimAndShoot extends Command{

    private boolean fired;

    public AimAndShoot()
    {
        addRequirements(Drivetrain.getInstance(), Shooter.getInstance(), Hood.getInstance());
    }

    public void initialize()
    {
        fired = false;
    }   

    @Override
    public void execute()
    {
        Translation2d hubCenter = Constants.Simulation.HUB_CONTENTS.getCenter().getTranslation();
        Translation2d robotPos = Drivetrain.getInstance().getPose().getTranslation();

        double targetYaw = Radians.of(Math.atan2(hubCenter.getY() - robotPos.getY(), hubCenter.getX() - robotPos.getX())).in(Rotations);

        double currentYaw = Drivetrain.getInstance().getPose().getRotation().getRotations();

        double yawError = targetYaw - currentYaw;
        
        yawError += 0.5;
        yawError %= 1;
        if (yawError < 0) yawError += 1;
        yawError -= 0.5;

        if (Math.abs(yawError) > Constants.AutoAim.YAW_TOLERANCE_ROTATIONS)
        {
            Drivetrain.getInstance().rotate(yawError * Constants.AutoAim.YAW_KP);
            return;
        }

        Drivetrain.getInstance().rotate(0.0);

        if (!fired)
        {
            fired = true;

            if (!Constants.DATA_COLLECTION_MODE)
            {
                if (!(RobotContainer.getInstance().shotMapEntries > 0))
                {
                    System.out.println("No shot data");
                    return;
                }

                double distance = robotPos.getDistance(hubCenter);
                Pair<LinearVelocity, Angle> shot = RobotContainer.getInstance().shotMap.get(distance);

                Shooter.getInstance().setVelocity(shot.getFirst());
                Hood.getInstance().setAngle(shot.getSecond());
            }
            else
            {
                Shooter.getInstance().setVelocity(Telemetry.getManualSpeedValue());
                Hood.getInstance().setAngle(Telemetry.getManualAngleValue());
            }

            SimulationController.getInstance().addBall();
        }

    }

    @Override
    public boolean isFinished()
    {
        return fired;
    }

    @Override
    public void end(boolean interrupted)
    {
        Drivetrain.getInstance().rotate(0.0);
    }
}
