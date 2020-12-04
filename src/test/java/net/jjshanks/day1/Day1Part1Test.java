package net.jjshanks.day1;

import org.junit.Test;

import net.jjshanks.AbstractProblem;
import net.jjshanks.ProblemTestCase;
import net.jjshanks.error.JollyException;

public class Day1Part1Test extends ProblemTestCase {

    @Test
    public void testSimple() throws JollyException {
        AbstractProblem solver = new Day1Part1("day1/simpleInput");
        solver.run();
        assertSolution(2019);
    }

}
