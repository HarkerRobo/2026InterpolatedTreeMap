
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Hood;

public class SetHoodAngle extends Command
{
    double angle;
    public SetHoodAngle(double angle)
    {
        this.angle = angle;
        addRequirements(Hood.getInstance());
        Hood.getInstance().hoodAngle = angle;
    }

    @Override
    public void initialize()
    {

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