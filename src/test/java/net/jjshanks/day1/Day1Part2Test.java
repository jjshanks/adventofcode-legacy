package net.jjshanks.day1;

import org.junit.Test;

import net.jjshanks.AbstractProblem;
import net.jjshanks.ProblemTestCase;
import net.jjshanks.error.JollyException;

public class Day1Part2Test extends ProblemTestCase {

    @Test
    public void testSimple() throws JollyException {
        AbstractProblem solver = new Day1Part2("day1/simpleInput");
        solver.run();
        assertSolution("The solution is: 12090");
    }

}
