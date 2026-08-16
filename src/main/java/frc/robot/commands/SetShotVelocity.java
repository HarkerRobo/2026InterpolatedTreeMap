
package frc.robot.commands;

import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class SetShotVelocity extends Command
{
    double velocity;
    public SetShotVelocity(LinearVelocity velocity)
    {
        addRequirements(Shooter.getInstance());
        this.velocity = velocity.in(MetersPerSecond);
    }

    @Override
    public void initialize()
    {
        Shooter.getInstance().setVelocity(MetersPerSecond.of(velocity));
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