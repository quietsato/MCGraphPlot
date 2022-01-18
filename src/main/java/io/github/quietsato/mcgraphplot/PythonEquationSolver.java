package io.github.quietsato.mcgraphplot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PythonEquationSolver extends Loggable implements EquationSolver {
    private String pythonExecCommand;

    public PythonEquationSolver(String pythonExecCommand) {
        this.pythonExecCommand =  pythonExecCommand;
    }

    @Override
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
        List<Coordinate> coordinateList = new ArrayList<>();

        Process process = execPythonScript();
        if (process == null) {
            return null;
        }

        InputStream inputStream = process.getInputStream();
        OutputStream outputStream = process.getOutputStream();

        // Input
        try {
            outputStream.write(String.format("%f %f\n", xmin, xmax).getBytes(StandardCharsets.UTF_8));
            outputStream.write(String.format("%f %f\n", ymin, ymax).getBytes(StandardCharsets.UTF_8));
            outputStream.write(String.format("%f %f\n", zmin, zmax).getBytes(StandardCharsets.UTF_8));
            outputStream.write(String.format("%f\n", step).getBytes(StandardCharsets.UTF_8));
            outputStream.write((equation + "\n").getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            return null;
        }

        // Output
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\n");
        while (scanner.hasNext()) {
            String s = scanner.next();
            String[] sp = s.split(" ");
            if (sp.length < 3) continue;

            try {
                Coordinate coordinate = new Coordinate(
                        Double.parseDouble(sp[0]),
                        Double.parseDouble(sp[1]),
                        Double.parseDouble(sp[2])
                );
                coordinateList.add(coordinate);
            } catch (NumberFormatException ignored) {
            }
        }

        return coordinateList;
    }

    private Process execPythonScript() {
        String pythonScript = this.loadPythonScriptFromResource();
        if (pythonScript == null) {
            return null;
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(this.pythonExecCommand, "-c", pythonScript);
            return processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadPythonScriptFromResource() {
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream("eq_solver.py");
        if (resourceStream == null) {
            return null;
        }
        Scanner s = new Scanner(resourceStream).useDelimiter("\\n");
        StringBuilder py = new StringBuilder();
        while (s.hasNext()) {
            py.append(s.next());
            py.append("\n");
        }
        return py.toString();
    }
}
