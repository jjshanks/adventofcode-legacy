package net.jjshanks;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractProblem {

    private static final Logger LOGGER = Logger.getLogger("Aoc2020");

    protected AbstractProblem() {
    }

    void solution(Object solution) {
        LOGGER.log(Level.INFO, "The solution is: {0}", solution);
    }
}
