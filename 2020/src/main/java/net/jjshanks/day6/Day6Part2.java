package net.jjshanks.day6;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day6Part2 extends AbstractProblem {

    private InputReader<GroupAnswers> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day6Part2.class);

    public static void main(String... args) throws Exception {
        new Day6Part2().run();
    }

    Day6Part2(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day6Part2() {
        this("input6");
    }

    @Override
    public void run() throws JollyException {
        List<GroupAnswers> answers = inputReader.getInput(GroupAnswers::parse, Collectors.toList(), "[\r\n][\r\n]+");
        long ans = answers.stream().map(GroupAnswers::part2).reduce(0l, Long::sum);
        solution(ans);
    }    
}