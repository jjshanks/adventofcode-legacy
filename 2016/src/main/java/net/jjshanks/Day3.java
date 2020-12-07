package net.jjshanks;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.error.JollyException;

public class Day3 extends AbstractProblem {
    private InputReader<String> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day3.class);

    public static void main(String... args) throws Exception {
        new Day3().run();
    }

    Day3(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day3() {
        this("input3");
    }

    
    @Override
    public void run() throws JollyException {
        part1();
        part2();
    }

    private void part1() throws JollyException {
        List<String> lines = inputReader.getInput(Function.identity());
        int sum = 0;
        Pattern p = Pattern.compile("([0-9]+) *([0-9]+) *([0-9]+)"); 
        for(String line : lines) {
            Matcher m = p.matcher(line);
            m.matches();
            int a = Integer.parseInt(m.group(1));
            int b = Integer.parseInt(m.group(2));
            int c = Integer.parseInt(m.group(3));
            if(a + b > c && b + c > a && a + c > b) {
                sum++;
            }
        }
        solution(sum);
    }

    private void part2() throws JollyException {
        List<String> lines = inputReader.getInput(Function.identity());
        int sum = 0;
        Pattern p = Pattern.compile("([0-9]+) *([0-9]+) *([0-9]+)"); 
        for(int i = 0; i < lines.size(); i += 3) {
            String line1 = lines.get(i);
            String line2 = lines.get(i+1);
            String line3 = lines.get(i+2);
            Matcher m1 = p.matcher(line1);
            m1.matches();
            Matcher m2 = p.matcher(line2);
            m2.matches();
            Matcher m3 = p.matcher(line3);
            m3.matches();
            for(int j = 1; j <= 3; j++) {
                int a = Integer.parseInt(m1.group(j));
                int b = Integer.parseInt(m2.group(j));
                int c = Integer.parseInt(m3.group(j));
                if(a + b > c && b + c > a && a + c > b) {
                    sum++;
                }
            }
        }
        solution(sum);
    }
}
