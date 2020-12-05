=begin
This solution is identical to part2.rb but the array is padded to avoid bound checks
Instead of the corners being [0,0],[0,99],[99,0],[99,99] they are [1,1],[1,100],[100,1],[100,100]
with indexes 0 and 101 being valid but unused as input

For the given input the difference is about 0.05 seconds on average ~0.75 vs ~0.7
=end

@on = 1 << 4
@always_on = 1 << 5
@size = 100
steps = 100 

lights = []
(@size+2).times do |i|
  lights[i] = []
  (@size+2).times do |j|
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

def update_neighbors(lights, x, y, delta)
  return if always_on?(lights[x][y])
  (-1..1).each do |i|
    (-1..1).each do |j|
      next if i == 0 and j == 0
      lights[x+i][y+j] += delta
    end
  end
  lights[x][y] = flip(lights[x][y])
end

def update_lights(lights)
  queue = []
  (1..@size).each do |x|
    (1..@size).each do |y|
      val = lights[x][y]
      if is_on?(val)
        unless [2,3].include?(neighbors(val))
          queue << [x,y,-1]
        end
      elsif neighbors(val) == 3
        queue << [x,y,1]
      end
    end
  end

  queue.each do |x,y,d|
    update_neighbors(lights,x,y,d)
  end
end


File.read("input").lines.each_with_index do |line,line_num|
  line.strip.split("").each_with_index do |char,char_num|
    update_neighbors(lights, line_num+1, char_num+1, 1) if char == "#"
  end
end

m = @size
[[1,1],[1,m],[m,1],[m,m]].each do |x,y|
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
