package frc.robot.simulation;

import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Constants;

public class BallState
{
    public double positionX;
    public double positionY;
    public double positionZ;
    public double velocityX;
    public double velocityY;
    public double velocityZ;
    public double 
    public Translation3d position;
    public Translation3d velocity;
    public static Translation3d acceleration = new Translation3d(0.0, 0.0, 
        Constants.Simulation.BALL_MASS * Constants.Simulation.G);

    public BallState ()
    {
    }

    public void step (double timeElapsed)
    {
        position = position.plus(velocity.times(timeElapsed));
        velocity = velocity.plus(acceleration.times(timeElapsed));
    }
}
