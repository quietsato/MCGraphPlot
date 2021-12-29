package io.github.quietsato.mcgraphplot;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class MCGraphPlot extends JavaPlugin {
    private World world;

    private EquationSolver equationSolver;
    private GraphPlotter graphPlotter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.world = getServer().getWorlds().get(0);

        PythonClient pythonClient = new PythonClient();
        pythonClient.setLogger(getLogger());
        this.equationSolver = new EquationSolver(pythonClient);

        this.graphPlotter = new GraphPlotter(this.world);
        this.graphPlotter.setLogger(getLogger());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("gp") && !label.equalsIgnoreCase("glaphplot")) {
            return false;
        }
        if (args.length < 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("plot")) {
            if (args.length < 2) {
                sender.sendMessage("Command argument `equation` is not provided.");
                return false;
            }
            String equation = Arrays.stream(args).skip(1).collect(Collectors.joining());


            List<Coordinate> coordinateList = this.equationSolver.solve(
                    -10.0, 10.0,
                    -10.0, 10.0,
                    -10.0, 10.0,
                    .1,
                    equation
            );

            Player player = sender.getServer().getPlayer(sender.getName());
            if (player == null) {
                sender.sendMessage("Command sender is not a player.");
                for (Coordinate c : coordinateList) {
                    getLogger().info(c.toString());
                }
                return true;
            }

            this.graphPlotter.plot(coordinateList, player.getEyeLocation().clone().toVector());

            return true;
        } else if (args[0].equalsIgnoreCase("config")) {
//             TODO: implement config command
//            if (getConfig().get(sender.getName()) == null) {
//
//            }
//

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
