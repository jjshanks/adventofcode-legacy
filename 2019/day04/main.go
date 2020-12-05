package main

import (
	"fmt"
)

func main() {
	low := 273025
	high := 767253
	count := 0
	for ; low <= high; low++ {
		if test1(low) {
			count++
		}
	}
	fmt.Println(count)
}

func test1(v int) bool {
	digits := make(map[int]int)
	decreasing := true
	lastDigit := v % 10
	digits[lastDigit] = 1
	v /= 10
	for v > 0 {
		digit := v % 10
		if _, ok := digits[digit]; !ok {
			digits[digit] = 0
		}
		digits[digit]++
		if digit > lastDigit {
			decreasing = false
		}
		lastDigit = v % 10
		v /= 10
	}
	foundDouble := false
	for _, v := range digits {
		if v == 2 {
			foundDouble = true
		}
	}
	return decreasing && foundDouble
}

