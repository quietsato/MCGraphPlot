package io.github.quietsato.mcgraphplot;

import org.bukkit.util.Vector;

public class Coordinate {
    public double x;
    public double y;
    public double z;

    Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    Vector toBukkitVector() {
        return new Vector(x, y, z);
    }
}
