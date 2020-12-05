package net.jjshanks.day5;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day5Part1 extends AbstractProblem {

    private InputReader<Pass> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day5Part1.class);

    public static void main(String... args) throws Exception {
        new Day5Part1().run();
    }

    Day5Part1(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day5Part1() {
        this("input5");
    }

    @Override
    public void run() throws JollyException {
        List<Pass> passes = inputReader.getInput(Pass::parse);
        int max = passes.
            stream().
            map(Pass::getSeatId).
            max(Integer::compare).
            get();
        solution(max);
    }
}
