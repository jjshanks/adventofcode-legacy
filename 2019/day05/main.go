package main

import (
	"fmt"
	"github.com/jjshanks/advent-of-code-2019/utils"
	"strconv"
	"strings"
)

func main() {
	input := utils.ReadInputLines("day05")
	//input = []string{"3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"}
	parts := strings.Split(input[0], ",")
	positions := make([]int, len(parts))
	for idx, s := range parts {
		positions[idx], _ = strconv.Atoi(s)
	}

	p := Program{Positions:positions}
	p.execute()
}

func ParseInstruction(input int) Instruction {
	modes := make([]int, 3)

	opcode := input % 100
	input /= 100

	modes[0] = input % 10
	modes[1] = input % 100 / 10
	modes[2] = input % 1000 / 100

	return Instruction{
		Opcode: opcode,
		modes:  modes,
	}
}

type Instruction struct {
	Opcode int
	modes []int
}

func (i Instruction) execute(position int, positions []int) (newPos int) {
	switch i.Opcode {
	case 1:
		left := i.getParam(position + 1, positions, i.modes[0])
		right := i.getParam(position + 2, positions, i.modes[1])
		idx := positions[position + 3]
		positions[idx] = left + right
		newPos = position + 4
		//fmt.Printf("positions[%d] = %d + %d\n", idx, left, right)
	case 2:
		left := i.getParam(position + 1, positions, i.modes[0])
		right := i.getParam(position + 2, positions, i.modes[1])
		idx := positions[position + 3]
		positions[idx] = left * right
		newPos = position + 4
		//fmt.Printf("positions[%d] = %d * %d\n", idx, left, right)
	case 3:
		// input
		idx := positions[position + 1]
		positions[idx] = 5
		newPos = position + 2
		//fmt.Printf("Input 1 into position %d\n", idx)
	case 4:
		// output
		fmt.Println(i.getParam(position + 1, positions, i.modes[0]))
		newPos = position + 2
		//fmt.Printf("Output position\n")
	case 5:
		// jump if true
		test := i.getParam(position + 1, positions, i.modes[0])
		if test != 0 {
			newPos = i.getParam(position + 2, positions, i.modes[1])
		} else {
			newPos = position + 3
		}
	case 6:
		// jump if false
		test := i.getParam(position + 1, positions, i.modes[0])
		if test == 0 {
			newPos = i.getParam(position + 2, positions, i.modes[1])
		} else {
			newPos = position + 3
		}
	case 7:
		// less than
		left := i.getParam(position + 1, positions, i.modes[0])
		right := i.getParam(position + 2, positions, i.modes[1])
		idx := positions[position + 3]
		if left < right {
			positions[idx] = 1
		} else {
			positions[idx] = 0
		}
		newPos = position + 4
	case 8:
		// equals
		left := i.getParam(position + 1, positions, i.modes[0])
		right := i.getParam(position + 2, positions, i.modes[1])
		idx := positions[position + 3]
		if left == right {
			positions[idx] = 1
		} else {
			positions[idx] = 0
		}
		newPos = position + 4
	default:
		panic(":(")
	}
	return
}

func (I Instruction) getParam(position int, positions []int, mode int) (param int) {
	idx := 0
	switch mode {
	case 0:
		idx = positions[position]
	case 1:
		idx = position
	default:
		panic(":(")
	}
	return positions[idx]
}

type Program struct {
	Positions []int
}

func (p Program) execute() {
	position := 0
	for p.Positions[position] != 99 {
		instruction := ParseInstruction(p.Positions[position])
		position = instruction.execute(position, p.Positions)
	}
	fmt.Println(p.Positions)
}

