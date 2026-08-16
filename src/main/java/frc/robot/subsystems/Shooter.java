package frc.robot.subsystems;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MathUtil;

public class Shooter extends SubsystemBase
{
    private double velocity;

    private static Shooter instance;

    private Shooter()
    {
    }
    
    public void setAngle(LinearVelocity velocity)
    {
        this.velocity = MathUtil.clamp(velocity.in(MetersPerSecond), 
            Constants.Shooter.MINIMUM_SPEED.in(MetersPerSecond),
            Constants.Shooter.MAXIMUM_SPEED.in(MetersPerSecond));
    }

    public LinearVelocity getVelocity()
    {
        return MetersPerSecond.of(velocity);
    }
    
    public static Shooter getInstance()
    {
        if(instance==null)
            instance=new Shooter();
        return instance;
    }

}