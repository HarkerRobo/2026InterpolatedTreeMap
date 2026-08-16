
package frc.robot.commands;

import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Hood;

public class SetHoodAngle extends Command
{
    double angle;
    public SetHoodAngle(Angle angle)
    {
        this.angle = angle.in(Rotations);
        addRequirements(Hood.getInstance());
    }

    @Override
    public void initialize()
    {
        Hood.getInstance().setAngle(Rotations.of(angle));
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return true;
    }

    @Override
    public void end(boolean interrupted)
    {

    }

}