package io.github.quietsato.mcgraphplot;

import java.util.logging.Logger;

public abstract class Loggable {
    Logger logger = null;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
