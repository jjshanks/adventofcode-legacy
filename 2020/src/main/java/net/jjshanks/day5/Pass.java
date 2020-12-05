package net.jjshanks.day5;

public class Pass {
    int row;
    int col;

    int getSeatId() {
        return row * 8 + col;
    }

    static Pass parse(String line) {
        Pass pass = new Pass();
        int low = 0;
        int high = 127;
        for(int i = 0; i < 6; i++) {
            if(line.charAt(i) == 'F') {
                high = (low + high) / 2;
            } else {
                low = (low + high) / 2 + 1;
            }
        }
        if(line.charAt(6) == 'F') {
            pass.row = low;
        } else {
            pass.row = high;
        }
        low = 0;
        high = 7;
        for(int i = 7; i < 9; i++) {
            if(line.charAt(i) == 'L') {
                high = (low + high) / 2;
            } else {
                low = (low + high) / 2 + 1;
            }
        }
        if(line.charAt(9) == 'L') {
            pass.col = low;
        } else {
            pass.col = high;
        }
        return pass;
    }
}
