package main

import (
	"fmt"
	"github.com/jjshanks/advent-of-code-2019/utils"
	"strconv"
)

func main() {
	totalFuel := 0
	input := utils.ReadInputLines("day01")
	for _, rawMass := range input {
		mass, _ := strconv.Atoi(rawMass)
		fuel := mass / 3 - 2
		totalFuel += fuel
		for ;; {
			fuel = fuel / 3 - 2
			if fuel > 0 {
				totalFuel += fuel
			} else {
				break
			}
		}
	}
	fmt.Println(totalFuel)
}
