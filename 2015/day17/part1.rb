=begin
--- Day 17: No Such Thing as Too Much ---

The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to move it into smaller containers. You take an inventory of the capacities of the available containers.

For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters. If you need to store 25 liters, there are four ways to do it:

15 and 10
20 and 5 (the first 5)
20 and 5 (the second 5)
15, 5, and 5
Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?
=end

containers = []
File.open("input") do |f|
  f.each_line do |line|
    containers << line.to_i
  end
end

# recursively find the sum for a[idx] + a[idx+1] .. a[idx] + a[N]
# while the total is less than or equal to the seek value
# returns the number of combos matching seek starting with idx
def find_sum(a, total, idx, seek)
  sum = 0
  total += a[idx]
  sum += 1 if total == seek
  return sum if total >= seek
  for i in (idx+1)..(a.size-1)
    sum += find_sum(a, total, i, seek)
  end
  return sum
end
total = 0
for i in (0..(containers.size-1))
  total += find_sum(containers, 0, i, 150)
end
p total
