package net.jjshanks.day3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day3Part2 extends AbstractProblem {

    private InputReader<TobogganMapRow> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day3Part2.class);

    public static void main(String... args) throws Exception {
        new Day3Part2().run();
    }

    Day3Part2(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day3Part2() {
        this("input3");
    }

    @Override
    public void run() throws JollyException {
        List<TobogganMapRow> values = inputReader.getInput(TobogganMapRow::parse);
        int[][] slopes = {{1,1}, {3,1}, {5,1}, {7,1}, {1,2}};
        BigInteger total = BigInteger.ONE;
        for(int[] slope : slopes) {
            // if(slope[0] != 3) {
            //     continue;
            // }
            TobogganMap map = TobogganMap.fromRows(values, slope[1], slope[0]);
            BigInteger result = BigInteger.valueOf(map.treeHits());
            total = total.multiply(result);
        }
        solution(total);
    }

    static class TobogganMap {
        List<TobogganMapRow> rows;
        int row = 0;
        int col = 0;
        int rowDelta;
        int colDelta;

        public static TobogganMap fromRows(List<TobogganMapRow> rows, int rowDelta, int colDelta) {
            TobogganMap map = new TobogganMap();
            map.rows = rows;
            map.rowDelta = rowDelta;
            map.colDelta = colDelta;
            return map;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

        void move() {
            row += rowDelta;
            col += colDelta;
        }

        boolean isDone() {
            return row + 1 == rows.size();
        }

        boolean isOnTree() {
            int realCol = col % rows.get(0).trees.size();
            return rows.get(row).trees.get(realCol);
        }

        int treeHits() {
            int trees = 0;
            while(!this.isDone()) {
                int oldRow = this.row;
                int oldCol = this.col;
                this.move();
                LOG.trace("Moving from {},{} to {},{}", oldRow, oldCol, this.row, this.col);
                LOG.trace("On a tree? {}", this.isOnTree());
                if(this.isOnTree()) {
                    trees++;
                }
            }
            return trees;
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
