package net.jjshanks.day6;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupAnswers {
    long count;
    Map<String, Long> questions;

    static GroupAnswers parse(String data) {
        GroupAnswers ga = new GroupAnswers();
        ga.count = data.lines().count();
        ga.questions = data.lines().map(s -> s.split("")).flatMap(Stream::of).collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        return ga;
    }

    long part2() {
        return questions.values().stream().filter(n -> n == count).count();
    } 
}
