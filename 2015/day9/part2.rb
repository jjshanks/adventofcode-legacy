=begin
--- Day 9: All in a Single Night ---

Every year, Santa manages to deliver all of his presents in a single night.

This year, however, he has some new locations to visit; his elves have provided him the distances between every pair of locations. He can start and end at any two (different) locations he wants, but he must visit each location exactly once. What is the shortest distance he can travel to achieve this?

For example, given the following distances:

London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
The possible routes are therefore:

Dublin -> London -> Belfast = 982
London -> Dublin -> Belfast = 605
London -> Belfast -> Dublin = 659
Dublin -> Belfast -> London = 659
Belfast -> Dublin -> London = 605
Belfast -> London -> Dublin = 982
The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.

What is the distance of the shortest route?

--- Part Two ---

The next year, just to show off, Santa decides to take the route with the longest distance instead.

He can still start and end at any two (different) locations he wants, and he still must visit each location exactly once.

For example, given the distances above, the longest route would be 982 via (for example) Dublin -> London -> Belfast.

What is the distance of the longest route?
=end

distances = {}

def get_distance(cities, distances)
  total = 0
  for i in 0..(cities.size - 2)
    total += distances[cities[i]][cities[i+1]]
  end
  return total
end

File.open("input") do |f|
  f.each_line do |line|
    parts = line.split(" ")
    city1 = parts[0]
    city2 = parts[2]
    dist = parts[4].to_i
    distances[city1] ||= {}
    distances[city2] ||= {}
    distances[city1][city2] = dist
    distances[city2][city1] = dist
  end
end

cities = distances.keys
max_dist = get_distance(cities, distances)
control = []
for i in 0..cities.size
  control << i
end
i = 1
while i < cities.size do
  control[i] -= 1
  j = i % 2 == 1 ? control[i] : 0
  tmp = cities[i]
  cities[i] = cities[j]
  cities[j] = tmp
  i = 1
  while control[i] == 0 do
    control[i] = i
    i += 1
  end
  max_dist = [max_dist, get_distance(cities, distances)].max
end
puts max_dist
