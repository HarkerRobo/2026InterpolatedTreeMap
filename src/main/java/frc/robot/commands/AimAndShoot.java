package frc.robot.commands;

import edu.wpi.first.math.Pair;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.simulation.SimulationController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;

public class AimAndShoot extends Command{

    public void initialize()
    {
        if (!(RobotContainer.getInstance().shotMapEntries > 0))
        {
            System.out.println("No shot data");
            return;
        }
        double distance = Drivetrain.getInstance().getPose().getTranslation().getDistance(Constants.Simulation.HUB_CONTENTS.getCenter().getTranslation());
        Pair<LinearVelocity, Angle> shot = RobotContainer.getInstance().shotMap.get(distance);
    
        Shooter.getInstance().setVelocity(shot.getFirst());
        Hood.getInstance().setAngle(shot.getSecond());

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
