package main

import (
	"fmt"
	"github.com/jjshanks/advent-of-code-2019/utils"
	"strconv"
	"strings"
)

type Pos struct {
	X int
	Y int
}

func (p Pos) Move(move PosDiff) Pos {
	return NewPos(p.X + move.X, p.Y + move.Y)
}

func (d Data) Move() Data {
	return NewData(d.Wire, d.Dist + 1)
}

type PosDiff struct {
	X int
	Y int
}

type Data struct {
	Wire int
	Dist int
}

func NewPos(X, Y int) Pos {
	return Pos{
		X: X,
		Y: Y,
	}
}

func NewData(wire, dist int) Data {
	return Data{
		Wire: wire,
		Dist: dist,
	}
}

func NewPostDiff(dir string) PosDiff {
	switch dir {
	case "U":
		return PosDiff{
			Y: 1,
		}
	case "D":
		return PosDiff{
			Y: -1,
		}
	case "L":
		return PosDiff{
			X: -1,
		}
	default:
		return PosDiff{
			X: 1,
		}
	}
}

func main() {
	input := utils.ReadInputLines("day03")
	//input[0] = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
	//input[1] = "U62,R66,U55,R34,D71,R55,D58,R83"
	//input[0] = "R8,U5,L5,D3"
	//input[1] = "U7,R6,D4,L4"

	visited := make(map[Pos]Data)

	for wire, program := range input {
		currentPos := NewPos(0,0)
		currentData := NewData(wire, 0)
		instructions := strings.Split(program, ",")
		fmt.Printf("%d => %s\n", wire, instructions)
		for _, instruction := range instructions {
			dir := NewPostDiff(instruction[0:1])
			length, _ := strconv.Atoi(instruction[1:])
			for ; length > 0 ; length-- {
				currentPos = currentPos.Move(dir)
				currentData = currentData.Move()
				if v, ok := visited[currentPos]; ok {
					if v.Wire != wire {
						//fmt.Printf("%v => %f\n", currentPos, math.Abs(float64(currentPos.X))+math.Abs(float64(currentPos.Y)))
						fmt.Printf("%d\n", v.Dist + currentData.Dist)
					}
				} else {
					visited[currentPos] = currentData
				}
			}
		}
	}
}
