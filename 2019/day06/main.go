package main

import (
	"fmt"
	"github.com/jjshanks/advent-of-code-2019/utils"
	"strings"
)

func main() {
	input := utils.ReadInputLines("day06")
	objects := make(map[string]*Object)
	for _, line := range input {
		parts := strings.Split(line, ")")
		if _, ok := objects[parts[0]]; !ok {
			o := Object{Name: parts[0]}
			objects[parts[0]] = &o
		}
		o := objects[parts[0]]
		if _, ok := objects[parts[1]]; !ok {
			s := Object{
				Name: parts[1],
				Orbiting: o,
			}
			objects[parts[1]]= &s
		} else {
			objects[parts[1]].Orbiting = o
		}
		s := objects[parts[1]]
		o.Satellites = append(o.Satellites, s)

	}
	com := objects["COM"]
	you := objects["YOU"]
	san := objects["SAN"]
	visited := make(map[*Object]int)
	youPath := []*Object{you}
	sanPath := []*Object{san}
	cost := 0
	for ; you != com ; cost++ {
		you = you.Orbiting
		youPath = append(youPath, you)
		visited[you] = cost
	}
	cost = 0
	for ; san != com ; cost++ {
		san = san.Orbiting
		sanPath = append(sanPath, san)
		if v, ok := visited[san]; ok {
			fmt.Println(san)
			fmt.Println(v)
			fmt.Println(cost)
			break
		}
		visited[san] = cost
	}
}

type Object struct {
	Name string
	Orbiting *Object
	Satellites []*Object
}

func (o *Object) String() string {
	return o.Name
}