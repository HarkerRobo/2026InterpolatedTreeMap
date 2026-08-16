package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.simulation.SimulationController;

public class ShootFuel extends Command
{
    public ShootFuel()
    {
    }

    @Override
    public void initialize()
    {
        SimulationController.getInstance().addBall();
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
