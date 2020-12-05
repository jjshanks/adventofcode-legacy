package net.jjshanks.day4;

import org.junit.Test;

import net.jjshanks.AbstractProblem;
import net.jjshanks.ProblemTestCase;
import net.jjshanks.error.JollyException;

public class Day4Part1Test extends ProblemTestCase {

    @Test
    public void testSimple() throws JollyException {
        AbstractProblem solver = new Day4Part1("day4/simpleInput");
        solver.run();
        assertSolution(2);
    }

}
