package net.jjshanks.day2;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day2Part1 extends AbstractProblem {

    private InputReader<PasswordEntry> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day2Part1.class);

    public static void main(String... args) throws Exception {
        new Day2Part1().run();
    }

    Day2Part1(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day2Part1() {
        this("input2");
    }

    @Override
    public void run() throws JollyException {
        List<PasswordEntry> values = inputReader.getInput(PasswordEntry::parse);
        int total = 0;
        LOG.debug("Processing {} enteries", values.size());
        for(PasswordEntry pe : values) {
            LOG.trace("Processing {}", pe);
            if(pe.valid()) {
                LOG.trace("Entry {} is valid", pe);
                total++;
            }
        }
        LOG.debug("Found {} valid passwords", total);
        solution(total);
    }

    static class PasswordEntry {
        int min;
        int max;
        char req;
        String password;

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

        boolean valid() {
            long count = password.chars().filter(ch -> ch == req).count();
            return count >= min && count <= max;
        }

        public static PasswordEntry parse(String s) {
            String[] parts = s.split(" ");
            String[] minMax = parts[0].split("-");
            char req = parts[1].charAt(0);
            String password = parts[2];
            PasswordEntry pe = new PasswordEntry();
            pe.min = Integer.parseInt(minMax[0]);
            pe.max = Integer.parseInt(minMax[1]);
            pe.req = req;
            pe.password = password;
            return pe;
        }
    }
}
