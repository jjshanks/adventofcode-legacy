package net.jjshanks.day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day4Part1 extends AbstractProblem {

    private InputReader<Passport> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day4Part1.class);

    public static void main(String... args) throws Exception {
        new Day4Part1().run();
    }

    Day4Part1(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day4Part1() {
        this("input4");
    }

    @Override
    public void run() throws JollyException {
        List<Passport> passports = inputReader.getInput(Passport::parse, Collectors.toList(), "[\r\n][\r\n]+");
        long validPassports = passports.stream().filter(Passport::isValid).count();
        solution(validPassports);
    }

    /**
     * byr (Birth Year)
     * iyr (Issue Year)
     * eyr (Expiration Year)
     * hgt (Height)
     * hcl (Hair Color)
     * ecl (Eye Color)
     * pid (Passport ID)
     * cid (Country ID)
     */
    static class Passport {
        static final Set<String> REQUIRED_FIELDS = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        static final Set<String> OPTIONAL_FIELDS = Set.of("cid");

        Map<String, String> fields = new HashMap<>();

        /**
         * hcl:#ae17e1 iyr:2013
         * eyr:2024
         * ecl:brn pid:760753108 byr:1931
         * hgt:179cm
         */
        static Passport parse(String lines) {
            Passport passport = new Passport();
            for(String line : lines.split("[\r\n]+")) {
                String[] lineParts = line.split(" ");
                for(String partLine : lineParts) {
                    String[] part = partLine.split(":");
                    passport.fields.put(part[0], part[1]);
                }
            }
            return passport;
        }

        boolean isValid() {
            for(String key : REQUIRED_FIELDS) {
                if(!fields.containsKey(key)) {
                    LOG.debug("Missing field {}", key);
                    return false;
                }
            }
            return true;
        }
    }

}