package net.jjshanks.day4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.jjshanks.AbstractProblem;
import net.jjshanks.ProblemTestCase;
import net.jjshanks.day4.Day4Part2.Passport;
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

    @Test
    public void testRangeValidator() throws JollyException {
        assertFalse(Passport.rangeValidator("bad", Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertFalse(Passport.rangeValidator("0", 1, 10));
        assertTrue(Passport.rangeValidator("1", 1, 10));
        assertTrue(Passport.rangeValidator("5", 1, 10));
        assertTrue(Passport.rangeValidator("10", 1, 10));
        assertFalse(Passport.rangeValidator("20", 1, 10));
    }

    @Test
    public void testHeightValidator() throws JollyException {
        assertFalse(Passport.heightValidator("bad"));
        assertTrue(Passport.heightValidator("165cm"));
        assertTrue(Passport.heightValidator("65in"));
        assertFalse(Passport.heightValidator("12"));
    }

}
