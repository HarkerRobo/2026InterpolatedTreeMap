package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase
{
    public double shotPower;

    public static Shooter instance;

    public Shooter()
    {

    }

    public static Shooter getInstance()
    {
        if(instance==null)
            instance=new Shooter();
        return instance;
    }

}