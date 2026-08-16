package frc.robot.commands;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class SwerveManual extends Command
{
    public SwerveManual()
    {
        addRequirements(Drivetrain.getInstance());
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        double leftRightAxis = RobotContainer.getInstance().driver.getRawAxis(0);
        double upDownAxis = -1.0 * RobotContainer.getInstance().driver.getRawAxis(1);

        if (Math.abs(leftRightAxis) < Constants.DEADBAND && Math.abs(upDownAxis) < Constants.DEADBAND)
        {
            Drivetrain.getInstance().drive(0.0);
            Drivetrain.getInstance().rotate(0.0);
            return;
        }

        double targetAngle = Radians.of(Math.atan2(upDownAxis, leftRightAxis)).in(Rotations);

        leftRightAxis = Math.abs(leftRightAxis);
        upDownAxis = Math.abs(upDownAxis);

        double maxMagnitude;
        if (upDownAxis > leftRightAxis)
        {
            maxMagnitude = Math.sqrt(1 + (leftRightAxis / upDownAxis) * (leftRightAxis / upDownAxis));
        }
        else
        {
            maxMagnitude = Math.sqrt(1 + (upDownAxis / leftRightAxis) * (upDownAxis / leftRightAxis));
        }
        double actualMagnitude = Math.sqrt(upDownAxis * upDownAxis + leftRightAxis * leftRightAxis);
        double relativeMagnitude = actualMagnitude / maxMagnitude;

        double currentAngle = Drivetrain.getInstance().getPose().getRotation().getRotations();

        System.out.printf("%f;%f\n", targetAngle, currentAngle);

        double relativeRotation = targetAngle - currentAngle;

        // find a shorter way, if it exists
        if (relativeRotation > 0.5) relativeRotation -= 1;
        if (relativeRotation < -0.5) relativeRotation += 1;
    
        // set relativeRotation to [-π, π]
        relativeRotation += 0.5;
        relativeRotation %= 1;
        relativeRotation -= 0.5;

        // normalize since the robot will never attempt to rotate more than π in any direction
        relativeRotation *= 2.0;


        System.out.printf("A: %f\n", relativeRotation);
        Drivetrain.getInstance().drive(relativeMagnitude * Constants.MAX_DRIVE_VOLTAGE);
        Drivetrain.getInstance().rotate(relativeRotation * Constants.MAX_ROTATE_VOLTAGE);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end(boolean interrupted)
    {
    }
}
