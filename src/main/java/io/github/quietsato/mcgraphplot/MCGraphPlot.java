package io.github.quietsato.mcgraphplot;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class MCGraphPlot extends JavaPlugin {

    private EquationSolverClient solverClient;
    private GraphPlotter graphPlotter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        String pythonExecCommand = "python3";

        World world = getServer().getWorlds().get(0);

        PythonEquationSolver solver = new PythonEquationSolver(pythonExecCommand);
        solver.setLogger(getLogger());
        this.solverClient = new EquationSolverClient(solver);

        this.graphPlotter = new GraphPlotter(world);
        this.graphPlotter.setLogger(getLogger());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("gp") && !label.equalsIgnoreCase("glaphplot")) {
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage("Command argument `equation` is not provided.");
            return false;
        }

        String equation = String.join("", args);

        List<Coordinate> coordinateList = this.solverClient.solve(
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
