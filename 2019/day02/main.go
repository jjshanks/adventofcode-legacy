package main

import (
	"fmt"
	"github.com/jjshanks/advent-of-code-2019/utils"
	"strconv"
	"strings"
)

func main() {
	input := utils.ReadInputLines("day02")

	parts := strings.Split(input[0], ",")
	positions := make([]int, len(parts))
	for idx, s := range parts {
		positions[idx], _ = strconv.Atoi(s)
	}
	pointer := 0
	for positions[pointer] != 99 {
		switch positions[pointer] {
		case 1:
			left := positions[positions[pointer+1]]
			right := positions[positions[pointer+2]]
			adx := positions[pointer+3]
			positions[adx] = left + right
			fmt.Printf("positions[%d] = %d + %d\n", adx, left, right)

		case 2:
			left := positions[positions[pointer+1]]
			right := positions[positions[pointer+2]]
			adx := positions[pointer+3]
			positions[adx] = left * right
			fmt.Printf("positions[%d] = %d * %d\n", adx, left, right)
		default:
			panic("boom")
		}
		pointer += 4
	}
	fmt.Println(positions)
}
