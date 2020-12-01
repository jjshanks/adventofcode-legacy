package net.jjshanks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProblem {

    static final Logger LOG = LoggerFactory.getLogger(AbstractProblem.class);

    protected AbstractProblem() {
    }

    void solution(Object solution) {
        LOG.info("The solution is: {}", solution);
    }
}
