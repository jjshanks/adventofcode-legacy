package net.jjshanks.day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day4part2 extends AbstractProblem {
    private InputReader<Passport> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day4part2.class);

    public static void main(String... args) throws Exception {
        new Day4part2().run();
    }

    Day4part2(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day4part2() {
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

        static final Map<String, Function<String, Boolean>> VALIDATORS = Map.of(
            "byr", s -> {int i = Integer.parseInt(s); return i >= 1920 && i <= 2002;},
            "iyr", s -> {int i = Integer.parseInt(s); return i >= 2010 && i <= 2020;},
            "eyr", s -> {int i = Integer.parseInt(s); return i >= 2020 && i <= 2030;},
            "hgt", Passport::heightValidator,
            "hcl", s -> Pattern.matches("#[0-9a-f]{6}", s),
            "ecl", s -> Pattern.matches("(amb|blu|brn|gry|grn|hzl|oth)", s),
            "pid", s -> s.length() == 9 && Integer.parseInt(s) >= 0
        );

        Map<String, String> fields = new HashMap<>();

        static boolean heightValidator(String s) {
            if(s.length() <= 3) {
                return false;
            }
            String unit = s.substring(s.length() - 2);
            String num = s.substring(0, s.length() - 2);
            int i = Integer.parseInt(num);
            switch(unit) {
                case "cm":
                    return i >= 150 && i <= 193;
                case "in":
                    return i >= 59 && i <= 76;
                default:
                    return false;
            }

        }

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
                if(VALIDATORS.containsKey(key) && Boolean.FALSE.equals(VALIDATORS.get(key).apply(fields.get(key)))) {
                        LOG.debug("Field {} with value {} is not valid", key, fields.get(key));
                        return false;
                }
            }
            return true;
        }
    }
}
