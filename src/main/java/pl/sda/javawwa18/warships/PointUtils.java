package pl.sda.javawwa18.warships;

import java.awt.*;

public class PointUtils {

    public static Point next(Point point, Point end) {
        if (point.x == end.x) {
            if (point.y < end.y) {
                return new Point(point.x, point.y + 1);
            } else {
                return new Point(point.x, point.y - 1);
            }
        } else if (point.y == end.y) {
            if (point.x < end.x) {
                return new Point(point.x + 1, point.y);
            } else {
                return new Point(point.x - 1, point.y);
            }
        } else {
            throw new IllegalArgumentException("Punkty muszą być równoległe");
        }
    }
}
