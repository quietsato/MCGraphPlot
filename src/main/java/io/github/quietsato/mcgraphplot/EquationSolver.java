package io.github.quietsato.mcgraphplot;

import java.util.List;

public interface EquationSolver {
    List<Coordinate> solve(
            double xmin,
            double xmax,
            double ymin,
            double ymax,
            double zmin,
            double zmax,
            double step,
            String equation
    );
}
