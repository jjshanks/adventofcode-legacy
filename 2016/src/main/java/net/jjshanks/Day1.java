package net.jjshanks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.error.JollyException;

public class Day1 extends AbstractProblem {

    private InputReader<Move> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day1.class);

    public static void main(String... args) throws Exception {
        new Day1().run();
    }

    Day1(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day1() {
        this("input1");
    }

    
    @Override
    public void run() throws JollyException {
        part1();
        part2();
    }

    void part1() throws JollyException {
        List<Move> moves = inputReader.getInput(Move::parse, Collectors.toList(), ", ");
        Pos pos = new Pos();
        for(Move move : moves) {
            LOG.debug("Doing move {}", move);
            pos.move(move);
            LOG.debug("Now at {}", pos);
        }
        solution(Math.abs(pos.x) + Math.abs(pos.y));
    }

    void part2() throws JollyException {
        List<Move> moves = inputReader.getInput(Move::parse, Collectors.toList(), ", ");
        Pos pos = new Pos();
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        visited.add(Pair.of(0,0));
        for(Move move : moves) {
            List<Pair<Integer, Integer>> visits = pos.move(move);
            for(Pair<Integer, Integer> visit : visits) {
                if(visited.contains(visit)) {
                    solution(Math.abs(visit.getLeft()) + Math.abs(visit.getRight()));
                    return;
                } else {
                    visited.add(visit);
                }
            }
        }
    }
    
    enum Facing {
        NORTH(-1,0),
        EAST(0,1),
        SOUTH(1,0),
        WEST(0,-1);
        
        int diffX;
        int diffY;

        public Facing turnLeft() {
            switch(this) {
                case NORTH:
                    return WEST;
                case EAST:
                    return NORTH;
                case SOUTH:
                    return EAST;
                case WEST:
                    return SOUTH;
            }
            return null;
        }

        public Facing turnRight() {
            switch(this) {
                case NORTH:
                    return EAST;
                case EAST:
                    return SOUTH;
                case SOUTH:
                    return WEST;
                case WEST:
                    return NORTH;
            }
            return null;
        }

        private Facing(int diffY, int diffX) {
            this.diffX = diffX;
            this.diffY = diffY;
        }
    }

    static class Pos {
        int x = 0;
        int y = 0;
        Facing facing = Facing.NORTH;

        public List<Pair<Integer, Integer>> move(Move move) {
            List<Pair<Integer, Integer>> visits = new ArrayList<>();
            if(move.turn == 'L') {
                facing = facing.turnLeft();
            } else {
                facing = facing.turnRight();
            }
            for(int i = 0; i < move.dist; i++) {
                x += facing.diffX * 1;
                y += facing.diffY * 1;
                visits.add(Pair.of(x, y));
            }
            return visits;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    static class Move {
        char turn;
        int dist;

        static Move parse(String s) {
            Move m = new Move();
            m.turn = s.charAt(0);
            m.dist = Integer.parseInt(s.substring(1));
            return m;
        }

        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

}
