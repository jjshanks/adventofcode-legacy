Personal solutions for [Advent of Code](https://adventofcode.com/) 2020.

# How to run a solution
As an example if you wanted to run Day 1 Part 1 you would do the following:

```
> mvn compile exec:java -Dexec.mainClass="net.jjshanks.Day1Part1"
```

# Changing the log level
Update the following line in [logback.xml](/src/main/resources/logback.xml) to be the level you want:

```
<logger name="net.jjshanks" level="DEBUG"/>
```

The log level for a particular class can also be changed with a more specfic logger:

```
<logger name="net.jjshanks.Day1Part2" level="TRACE"/>
```
