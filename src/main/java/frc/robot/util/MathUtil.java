package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rectangle2d;

public class MathUtil 
{
    public static double clamp(double value, double min, double max)
    {
         if (value < min) return min;
         if (value > max) return max;
         return value;
    }

    public static boolean inRectifiedRectangle2d(double x, double y, Rectangle2d rect)
    {
        Pose2d center = rect.getCenter();
        double xp5 = center.getX();
        double yp5 = center.getY();
        double xl = rect.getXWidth();
        double yl = rect.getYWidth();
        double x0 = xp5 - xl/2.0;
        double x1 = xp5 + xl/2.0;
        double y0 = yp5 - yl/2.0;
        double y1 = yp5 + yl/2.0;

        return x > x0 && x < x1 && y > y0 && y < y1;
    }
}
