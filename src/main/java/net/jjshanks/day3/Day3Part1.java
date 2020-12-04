package net.jjshanks.day3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day3Part1 extends AbstractProblem {

    private InputReader inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day3Part2.class);

    public static void main(String... args) throws Exception {
        new Day3Part2().run();
    }

    Day3Part1(String inputPath) {
        this.inputReader = new InputReader(inputPath);
    }

    public Day3Part1() {
        this("input3");
    }

    @Override
    public void run() throws JollyException {
        List<TobogganMapRow> values = inputReader.getInput(TobogganMapRow::parse);
        TobogganMap map = TobogganMap.fromRows(values);
        int trees = 0;
        while(!map.isDone()) {
            int oldRow = map.row;
            int oldCol = map.col;
            map.move();
            LOG.debug("Moving from {},{} to {},{}", oldRow, oldCol, map.row, map.col);
            LOG.debug("On a tree? {}", map.isOnTree());
            if(map.isOnTree()) {
                trees++;
            }
        }
        solution(trees);
    }
    
    static class TobogganMap {
        List<TobogganMapRow> rows;
        int row = 0;
        int col = 0;

        public static TobogganMap fromRows(List<TobogganMapRow> rows) {
            TobogganMap map = new TobogganMap();
            map.rows = rows;
            return map;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

        void move() {
            row++;
            col += 3;
        }

        boolean isDone() {
            return row + 1 == rows.size();
        }

        boolean isOnTree() {
            int realCol = col % rows.get(0).trees.size();
            return rows.get(row).trees.get(realCol);
        }
    }

    static class TobogganMapRow {
        List<Boolean> trees;

        public static TobogganMapRow parse(String line) {
            String[] parts = line.split("");
            TobogganMapRow row = new TobogganMapRow();
            row.trees = new ArrayList<>(parts.length);
            for(String point : parts) {
                row.trees.add(point.equals("#"));
            }
            return row;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }
}
