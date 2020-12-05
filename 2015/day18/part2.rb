=begin
--- Day 18: Like a GIF For Your Yard ---

After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed. You arrange them in a 100x100 grid.

Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few lights, he says, you'll have to resort to animation.

Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a . means "off".

Then, animate your grid in steps, where each step decides the next configuration based on the current one. Each light's next state (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals). Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".

For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8, and the light marked B, which is on an edge, only has the neighbors marked 1 through 5:

1B5...
234...
......
..123.
..8A4.
..765.
The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:

A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
All of the lights update simultaneously; they all consider the same current state before moving to the next.

Here's a few steps from an example configuration of another 6x6 grid:

Initial state:
.#.#.#
...##.
#....#
..#...
#.#..#
####..

After 1 step:
..##..
..##.#
...##.
......
#.....
#.##..

After 2 steps:
..###.
......
..###.
......
.#....
.#....

After 3 steps:
...#..
......
...#..
..##..
......
......

After 4 steps:
......
......
..##..
..##..
......
......
After 4 steps, this example has four lights on.

In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?

--- Part Two ---

You flip the instructions over; Santa goes on to point out that this is all just an implementation of Conway's Game of Life. At least, it was, until you notice that something's wrong with the grid of lights you bought: four lights, one in each corner, are stuck on and can't be turned off. The example above will actually run like this:

Initial state:
##.#.#
...##.
#....#
..#...
#.#..#
####.#

After 1 step:
#.##.#
####.#
...##.
......
#...#.
#.####

After 2 steps:
#..#.#
#....#
.#.##.
...##.
.#..##
##.###

After 3 steps:
#...##
####.#
..##.#
......
##....
####.#

After 4 steps:
#.####
#....#
...#..
.##...
#.....
#.#..#

After 5 steps:
##.###
.##..#
.##...
.##...
#.#...
##...#
After 5 steps, this example now has 17 lights on.

In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the on state, how many lights are on after 100 steps?
=end

# Light values are stored in 6 bits
# Bit 6 is whether the light is always on (1) or not (0)
# Bit 5 is whether the light is on (1) or off (0)
# Bit 1-4 are any value from 0 to 8

# light on bit
@on = 1 << 4
# light always on bit
@always_on = 1 << 5
size = 100
steps = 100 

lights = []
size.times do |i|
  lights[i] = []
  size.times do |j|
    lights[i] << 0
  end
end

def flip(value)
  return is_on?(value) ? neighbors(value) : (@on | value)
end

def is_on?(value)
  return value & @on > 0
end

def always_on?(value)
  return value & @always_on > 0
end

def neighbors(value)
  return ~@on & value
end

# for each neighbor to lights[x,y] modify it by delta
# then flip lights[x,y]. do nothing is always on
def update_neighbors(lights, x, y, delta)
  return if always_on?(lights[x][y])
  (-1..1).each do |i|
    next if x + i < 0 or x + i > lights.size - 1
    (-1..1).each do |j|
      next if y + j < 0 or y + j > lights.size - 1
      next if i == 0 and j == 0
      lights[x+i][y+j] += delta
    end
  end
  lights[x][y] = flip(lights[x][y])
end

# for each light determine whether it should be on or off
def update_lights(lights)
  # track changes in queue in [x,y,delta] format
  queue = []
  lights.each_with_index do |row, row_num|
    row.each_with_index do |val, col_num|
      if is_on?(val)
        unless [2,3].include?(neighbors(val))
          queue << [row_num,col_num,-1]
        end
      elsif neighbors(val) == 3
        queue << [row_num,col_num,1]
      end
    end
  end

  queue.each do |x,y,d|
    update_neighbors(lights,x,y,d)
  end
end


File.read("input").lines.each_with_index do |line,line_num|
  line.strip.split("").each_with_index do |char,char_num|
    update_neighbors(lights, line_num, char_num, 1) if char == "#"
  end
end

# turn on corners if off and set to always on
m = size - 1
[[0,0],[0,m],[m,0],[m,m]].each do |x,y|
  update_neighbors(lights, x, y, 1) unless is_on?(lights[x][y])
  lights[x][y] = lights[x][y] | @always_on
end

steps.times do
  update_lights(lights)
end

total = 0
lights.each do |row|
  row.each do |val|
    total += 1 if is_on?(val)
  end
end

p total
