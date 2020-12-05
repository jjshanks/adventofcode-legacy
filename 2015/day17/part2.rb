=begin
--- Day 17: No Such Thing as Too Much ---

The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to move it into smaller containers. You take an inventory of the capacities of the available containers.

For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters. If you need to store 25 liters, there are four ways to do it:

15 and 10
20 and 5 (the first 5)
20 and 5 (the second 5)
15, 5, and 5
Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?

--- Part Two ---

While playing with all the containers in the kitchen, another load of eggnog arrives! The shipping and receiving department is requesting as many containers as you can spare.

Find the minimum number of containers that can exactly fit all 150 liters of eggnog. How many different ways can you fill that number of containers and still hold exactly 150 litres?

In the example above, the minimum number of containers was two. There were three ways to use that many containers, and so the answer there would be 3.
=end

containers = []
File.open("input") do |f|
  f.each_line do |line|
    containers << line.to_i
  end
end

# map from number of containers to count of sum matches
$size_to_count = {}

# recursively find the sum for a[idx] + a[idx+1] .. a[idx] + a[N]
# while the total is less than or equal to the seek value
# $size_to_count is modified based on seek matches
def find_sum(a, total, idx, seek, size)
  total += a[idx]
  size += 1
  if total == seek
    $size_to_count[size] ||= 0
    $size_to_count[size] += 1
  end
  return if total >= seek
  for i in (idx+1)..(a.size-1)
    find_sum(a, total, i, seek, size)
  end
end

for i in (0..(containers.size-1))
  find_sum(containers, 0, i, 150, 0)
end

p $size_to_count[$size_to_count.keys.min]
