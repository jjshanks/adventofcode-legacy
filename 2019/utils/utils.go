package utils

import (
	"bufio"
	"os"
	"strconv"
)

func ReadInputLines(prefix string) []string {
	var result []string
	file, err := os.Open(prefix + "/input")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		result = append(result, scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		panic(err)
	}
	return result
}

func StringToIntSlice(strs []string) (ints []int){
	ints = make([]int, len(strs))
	for idx, s := range strs {
		v, err := strconv.Atoi(s)
		if err != nil {
			panic(err)
		}
		ints[idx] = v
	}
	return
}

type Pos struct {
	X int
	Y int
}

func (p Pos) Move(move PosDiff) Pos {
	return NewPos(p.X + move.X, p.Y + move.Y)
}

type PosDiff struct {
	X int
	Y int
}

func NewPos(X, Y int) Pos {
	return Pos{
		X: X,
		Y: Y,
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