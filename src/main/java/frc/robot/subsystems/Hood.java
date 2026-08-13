
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase
{
    public double hoodAngle;

    public static Hood instance;

    public Hood()
    {

    }

    public static Hood getInstance()
    {
        if(instance==null)
            instance=new Hood();
        return instance;
    }
    
}