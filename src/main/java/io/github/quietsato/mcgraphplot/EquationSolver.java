package io.github.quietsato.mcgraphplot;

import java.util.List;

public class EquationSolver extends Loggable {
    EquationSolverClient solverClient;

    public EquationSolver(EquationSolverClient solverClient) {
        this.solverClient = solverClient;
    }

    public List<Coordinate> solve(
            double xmin,
            double xmax,
            double ymin,
            double ymax,
            double zmin,
            double zmax,
            double step,
            String equation
    ) {
        return this.solverClient.solve(
                xmin,
                xmax,
                ymin,
                ymax,
                zmin,
                zmax,
                step,
                equation
        );
    }
}
