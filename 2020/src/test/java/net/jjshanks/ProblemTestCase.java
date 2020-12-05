package net.jjshanks;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

abstract public class ProblemTestCase {

    private ListAppender<ILoggingEvent> appender;
    private Logger solutionLogger = (Logger) LoggerFactory.getLogger(AbstractProblem.class);

    @Before
    public void problemSetup() {
      appender = new ListAppender<>();
      appender.start();
      solutionLogger.addAppender(appender);
    }

    @After
    public void problemTearDown() {
      solutionLogger.detachAppender(appender);
    }

    protected void assertSolution(Object expected) {
        String line = String.format("The solution is: %s", expected);
        List<String> logs = appender.list.stream().map(ILoggingEvent::getFormattedMessage).collect(Collectors.toList());
        assertTrue(String.format("Didn't find expected solution '%s' log line in %s.", line, logs), logs.contains(line));
    }

}
