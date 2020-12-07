package net.jjshanks;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.error.JollyException;

public class Day4 extends AbstractProblem {
    private InputReader<Room> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day4.class);

    public static void main(String... args) throws Exception {
        new Day4().run();
    }

    Day4(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day4() {
        this("input4");
    }

    @Override
    public void run() throws JollyException {
        part1();
        part2();
    }

    private void part1() throws JollyException {
        List<Room> rooms = inputReader.getInput(Room::parse);
        int ans = rooms.stream().filter(r -> r.isValid()).map(r -> r.sectorId).reduce(0, Integer::sum);
        solution(ans);
    }

    private void part2() throws JollyException {
        List<Room> rooms = inputReader.getInput(Room::parse);
        for(Room room : rooms) {
            String s = room.decrypt();
            if(s.contains("orth")) {
                LOG.info("{} - {}", s.replaceAll("[^a-z]", " "), room.sectorId);
            }
        }
    }

    static class Room {
        String encryptedName;
        int sectorId;
        String checksum;

        public static Room parse(String line) {
            Room room = new Room();
            String[] parts = line.split("-");
            room.encryptedName = IntStream.range(0, parts.length - 1).mapToObj(i -> parts[i])
                    .collect(Collectors.joining("-"));
            Pattern p = Pattern.compile("([0-9]+)\\[([a-z]{5})\\]");
            Matcher m = p.matcher(parts[parts.length - 1]);
            m.matches();
            room.sectorId = Integer.parseInt(m.group(1));
            room.checksum = m.group(2);
            return room;
        }

        public boolean isValid() {
            List<String> l = List.of(encryptedName.split(""));
            Map<String, Long> m = l.stream().filter(c -> !c.equals("-"))
                    .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
            String checksum = m.entrySet().stream()
                    .sorted((lhs, rhs) -> 
                        new CompareToBuilder().append(rhs.getValue(), lhs.getValue()).append(lhs.getKey(), rhs.getKey()).toComparison())
                    .limit(5)
                    .map(e -> e.getKey())
                    .collect(Collectors.joining(""));
            return checksum.equals(this.checksum);
        }

        public String decrypt() {
            int move = sectorId % 26;

            return encryptedName.chars().map(i -> i + move).map(i -> {
                if(i > 122) {
                    i -= 26;
                }
                return i;
            }).collect(StringBuilder::new, 
                                     StringBuilder::appendCodePoint, 
                                     StringBuilder::append) 
                            .toString(); 
        }

        public String toString() {
            return ReflectionToStringBuilder.reflectionToString(this);
        }
    }
}
