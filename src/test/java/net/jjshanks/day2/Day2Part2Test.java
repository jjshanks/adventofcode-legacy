package net.jjshanks.day2;

import org.junit.Test;

import net.jjshanks.AbstractProblem;
import net.jjshanks.ProblemTestCase;
import net.jjshanks.error.JollyException;

public class Day2Part2Test extends ProblemTestCase {

    @Test
    public void testSimple() throws JollyException {
        AbstractProblem solver = new Day2Part2("day2/simpleInput");
        solver.run();
        assertSolution("The solution is: 4");
    }

}