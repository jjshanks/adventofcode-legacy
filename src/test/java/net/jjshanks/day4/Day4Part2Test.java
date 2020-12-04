package net.jjshanks.day4;

import org.junit.Test;

import net.jjshanks.AbstractProblem;
import net.jjshanks.ProblemTestCase;
import net.jjshanks.error.JollyException;

public class Day4Part2Test extends ProblemTestCase {

    @Test
    public void testSimple() throws JollyException {
        AbstractProblem solver = new Day4Part2("day4/simpleInput");
        solver.run();
        assertSolution(2);
    }

    @Test
    public void testInvalid() throws JollyException {
        AbstractProblem solver = new Day4Part2("day4/invalidInput");
        solver.run();
        assertSolution(0);
    }

    @Test
    public void testValid() throws JollyException {
        AbstractProblem solver = new Day4Part2("day4/validInput");
        solver.run();
        assertSolution(4);
    }

}
