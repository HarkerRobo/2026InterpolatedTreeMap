// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.interpolation.InterpolatingTreeMap;
import edu.wpi.first.math.interpolation.Interpolator;
import edu.wpi.first.math.interpolation.InverseInterpolator;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.AimAndShoot;
import frc.robot.commands.SetHoodAngle;
import frc.robot.commands.SwerveManual;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;

public class RobotContainer
{
    private static RobotContainer instance;

    public CommandXboxController driver = new CommandXboxController(0);

    public int shotMapEntries = 0;

    private static final Interpolator<Pair<LinearVelocity, Angle>> SHOT_INTERPOLATOR =
    (start, end, t) ->
    {
        double vel = start.getFirst().in(MetersPerSecond)
                + t * (end.getFirst().in(MetersPerSecond) - start.getFirst().in(MetersPerSecond));

        double angle = start.getSecond().in(Rotations)
                + t * (end.getSecond().in(Rotations) - start.getSecond().in(Rotations));

        return new Pair<>(MetersPerSecond.of(vel), Rotations.of(angle));
    };

    public InterpolatingTreeMap<Double, Pair<LinearVelocity, Angle>> shotMap = new InterpolatingTreeMap<>(InverseInterpolator.forDouble(), SHOT_INTERPOLATOR);

    

    private RobotContainer()
    {
        Drivetrain.getInstance().setDefaultCommand(new SwerveManual());
        configureBindings();

        for (double[] point : Constants.ShotMap.POINTS)
        {
            double distance = point[0];
            LinearVelocity speed = MetersPerSecond.of(point[1]);
            Angle angle = Degrees.of(point[2]);

            shotMap.put(distance, new Pair<>(speed, angle));
        }

        shotMapEntries = Constants.ShotMap.POINTS.length;
    }

    private void configureBindings() 
    {
        driver.button(1).onTrue(new AimAndShoot());
    }



    public static RobotContainer getInstance()
    {
        if (instance == null) instance = new RobotContainer();
        return instance;
    }
}
