
package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MathUtil;

public class Hood extends SubsystemBase
{
    private double angle; // rotations

    private static Hood instance;

    private Hood()
    {
        angle = Constants.Hood.INITIAL_ANGLE.in(Rotations);
    }

    public void setAngle(Angle angle)
    {
        this.angle = MathUtil.clamp(angle.in(Rotations), 
            Constants.Hood.MINIMUM_ANGLE.in(Rotations),
            Constants.Hood.MAXIMUM_ANGLE.in(Rotations));
    }

    public Angle getAngle()
    {
        return Rotations.of(angle);
    }

    public static Hood getInstance()
    {
        if(instance == null)
            instance = new Hood();
        return instance;
    }
    
}