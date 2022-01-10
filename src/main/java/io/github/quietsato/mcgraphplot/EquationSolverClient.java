package io.github.quietsato.mcgraphplot;

import java.util.List;

public class EquationSolverClient extends Loggable {
    EquationSolver solver;

    public EquationSolverClient(EquationSolver solver) {
        this.solver = solver;
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
        return this.solver.solve(
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
