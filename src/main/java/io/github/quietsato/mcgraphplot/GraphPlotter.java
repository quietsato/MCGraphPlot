package io.github.quietsato.mcgraphplot;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.logging.Logger;

public class GraphPlotter extends Loggable {
    private final World world;

    public GraphPlotter(World world) {
        this.world = world;
    }

    void plot(List<Coordinate> coordinateList, Vector origin) {

        for (Coordinate c : coordinateList) {
            Vector v = c.toBukkitVector();

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.0f);
            Location particleLocation = origin.clone().add(v).toLocation(this.world);

            world.spawnParticle(
                    Particle.REDSTONE,
                    particleLocation,
                    1,
                    0, 0, 0,
                    dustOptions
            );
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
