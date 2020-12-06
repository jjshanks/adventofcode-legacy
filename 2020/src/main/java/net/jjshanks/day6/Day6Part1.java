package net.jjshanks.day6;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day6Part1 extends AbstractProblem {

    private InputReader<GroupAnswers> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day6Part1.class);

    public static void main(String... args) throws Exception {
        new Day6Part1().run();
    }

    Day6Part1(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day6Part1() {
        this("input6");
    }

    @Override
    public void run() throws JollyException {
        List<GroupAnswers> answers = inputReader.getInput(GroupAnswers::parse, Collectors.toList(), "[\r\n][\r\n]+");
        int ans = answers.stream().map(ga -> ga.questions.size()).reduce(0, Integer::sum);
        solution(ans);
    }
    
}