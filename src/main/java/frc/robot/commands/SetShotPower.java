
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class SetShotPower extends Command
{
    double power;
    public SetShotPower(double power)
    {
        addRequirements(Shooter.getInstance());
        this.power = power;
    }

    @Override
    public void initialize()
    {
        Shooter.getInstance().shotPower = power;
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