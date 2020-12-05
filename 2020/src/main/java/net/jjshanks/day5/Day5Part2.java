package net.jjshanks.day5;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day5Part2 extends AbstractProblem {

    private InputReader<Pass> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day5Part2.class);

    public static void main(String... args) throws Exception {
        new Day5Part2().run();
    }

    Day5Part2(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day5Part2() {
        this("input5");
    }

    @Override
    public void run() throws JollyException {
        List<Pass> passes = inputReader.getInput(Pass::parse);
        Set<Integer> seatIds = passes.stream().map(Pass::getSeatId).collect(Collectors.toSet());
        int seatId = IntStream.range(8, 1016).
            filter(i -> (
                seatIds.contains(i-1) && 
                seatIds.contains(i+1) && 
                !seatIds.contains(i)
            )).
            findFirst().
            getAsInt();
        solution(seatId);
    }
}
