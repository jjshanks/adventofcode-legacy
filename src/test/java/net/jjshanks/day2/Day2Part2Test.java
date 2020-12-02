package net.jjshanks.day2;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import junit.framework.TestCase;
import net.jjshanks.AbstractProblem;
import net.jjshanks.JollyException;

public class Day2Part2Test extends TestCase {

    private ListAppender<ILoggingEvent> appender;
    private Logger solutionLogger = (Logger) LoggerFactory.getLogger(AbstractProblem.class);

    @Before
    public void setUp() {
      appender = new ListAppender<>();
      appender.start();
      solutionLogger.addAppender(appender);
    }

    @After
    public void tearDown() {
      solutionLogger.detachAppender(appender);
    }

    public void testSimple() throws JollyException {
        AbstractProblem solver = new Day2Part2("day2/simpleInput");
        solver.run();
        List<String> logs = appender.list.stream().map(ILoggingEvent::getFormattedMessage).collect(Collectors.toList());
        assertTrue("Didn't find expected solution log line.", logs.contains("The solution is: 4"));
    }

}