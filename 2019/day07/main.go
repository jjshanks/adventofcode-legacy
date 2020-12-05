package main

import (
	"fmt"
	"github.com/jjshanks/advent-of-code-2019/utils"
	"strconv"
	"strings"
)

func main() {
	memory := utils.ReadInputLines("day07")
	//memory = []string{"3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"}
	parts := strings.Split(memory[0], ",")
	max := 0.0
	for _, x := range heapPermutations(intSlice([]int{5,6,7,8,9})) {
		aMemory := make([]int, len(parts))
		bMemory := make([]int, len(parts))
		cMemory := make([]int, len(parts))
		dMemory := make([]int, len(parts))
		eMemory := make([]int, len(parts))
		for idx, s := range parts {
			v, _ := strconv.Atoi(s)
			aMemory[idx] = v
			bMemory[idx] = v
			cMemory[idx] = v
			dMemory[idx] = v
			eMemory[idx] = v
		}
		modes := x.(intSlice)
		//modes := []int{9,8,7,6,5}
		eToA := make(chan int, 2)
		eToA <- modes[0]
		eToA <- 0
		aToB := make(chan int, 2)
		aToB <- modes[1]
		bToC := make(chan int, 2)
		bToC <- modes[2]
		cToD := make(chan int, 2)
		cToD <- modes[3]
		dToE := make(chan int, 2)
		dToE <- modes[4]

		a := Program{Memory: aMemory, Inputs: eToA, Outputs: aToB, Done: make(chan bool)}
		b := Program{Memory: bMemory, Inputs: aToB, Outputs: bToC, Done: make(chan bool)}
		c := Program{Memory: cMemory, Inputs: bToC, Outputs: cToD, Done: make(chan bool)}
		d := Program{Memory: dMemory, Inputs: cToD, Outputs: dToE, Done: make(chan bool)}
		e := Program{Memory: eMemory, Inputs: dToE, Outputs: eToA, Done: make(chan bool)}
		go func() {
			a.execute()
		}()
		go func() {
			b.execute()
		}()
		go func() {
			c.execute()
		}()
		go func() {
			d.execute()
		}()
		go func() {
			e.execute()
		}()
		<-a.Done
		<-b.Done
		<-c.Done
		<-d.Done
		<-e.Done
		val := float64(<- eToA)
		//fmt.Println(val)
		if val > max {
			max = val
		}
	}
	fmt.Printf("%d\n", int64(max))
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

func (i Instruction) execute(position int, program *Program) (newPos int) {
	switch i.Opcode {
	case 1:
		left := i.getParam(position + 1, program.Memory, i.modes[0])
		right := i.getParam(position + 2, program.Memory, i.modes[1])
		idx := program.Memory[position + 3]
		program.Memory[idx] = left + right
		newPos = position + 4
		//fmt.Printf("positions[%d] = %d + %d\n", idx, left, right)
	case 2:
		left := i.getParam(position + 1, program.Memory, i.modes[0])
		right := i.getParam(position + 2, program.Memory, i.modes[1])
		idx := program.Memory[position + 3]
		program.Memory[idx] = left * right
		newPos = position + 4
		//fmt.Printf("positions[%d] = %d * %d\n", idx, left, right)
	case 3:
		// input
		in := <-program.Inputs
		idx := program.Memory[position + 1]
		program.Memory[idx] = in
		newPos = position + 2
		// fmt.Printf("Input %d into position %d\n", in, idx)
	case 4:
		// output
		program.Outputs <- i.getParam(position + 1, program.Memory, i.modes[0])
		//fmt.Println(i.getParam(position + 1, program.Memory, i.modes[0]))
		newPos = position + 2
		//fmt.Printf("Output position\n")
	case 5:
		// jump if true
		test := i.getParam(position + 1, program.Memory, i.modes[0])
		if test != 0 {
			newPos = i.getParam(position + 2, program.Memory, i.modes[1])
		} else {
			newPos = position + 3
		}
	case 6:
		// jump if false
		test := i.getParam(position + 1, program.Memory, i.modes[0])
		if test == 0 {
			newPos = i.getParam(position + 2, program.Memory, i.modes[1])
		} else {
			newPos = position + 3
		}
	case 7:
		// less than
		left := i.getParam(position + 1, program.Memory, i.modes[0])
		right := i.getParam(position + 2, program.Memory, i.modes[1])
		idx := program.Memory[position + 3]
		if left < right {
			program.Memory[idx] = 1
		} else {
			program.Memory[idx] = 0
		}
		newPos = position + 4
	case 8:
		// equals
		left := i.getParam(position + 1, program.Memory, i.modes[0])
		right := i.getParam(position + 2, program.Memory, i.modes[1])
		idx := program.Memory[position + 3]
		if left == right {
			program.Memory[idx] = 1
		} else {
			program.Memory[idx] = 0
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
	Memory  []int
	Inputs  <-chan int
	Outputs chan<- int
	Done chan bool
}

func (p *Program) execute() {
	position := 0
	for p.Memory[position] != 99 {
		instruction := ParseInstruction(p.Memory[position])
		position = instruction.execute(position, p)
	}
	p.Done <- true
	//fmt.Println(p.Memory)
}

type intSlice []int

func (p intSlice) Len() int      { return len(p) }
func (p intSlice) Swap(i, j int) { p[i], p[j] = p[j], p[i] }
func (p intSlice) Copy() heapInterface {
	A := make(intSlice, p.Len())
	copy(A, p)
	return A
}

type heapInterface interface {
	// Len returns the number of elements in the collection
	Len() int
	// Swap swaps the elements with indexes i and j
	Swap(i, j int)
	// Copy copies the existing slice in a new slice
	Copy() heapInterface
}

func Ints(a []int) [][]int {
	interfaceSlice := heapPermutations(intSlice(a))
	permutations := make([][]int, len(interfaceSlice))

	// Assert and transfer each permuted item to our target-typed structure
	for i, d := range interfaceSlice {
		permutations[i] = d.(intSlice)
	}
	return permutations
}

func heapPermutations(data heapInterface) []heapInterface {
	permutations := []heapInterface{}
	var generate func(int, heapInterface)

	generate = func(n int, arr heapInterface) {
		if n == 1 {
			A := arr.Copy()
			permutations = append(permutations, A)
		} else {
			for i := 0; i < n; i++ {
				generate(n-1, arr)
				if n%2 == 0 {
					arr.Swap(i, n-1)
				} else {
					arr.Swap(0, n-1)
				}
			}
		}
	}
	generate(data.Len(), data)
	return permutations
}