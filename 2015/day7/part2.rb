=begin
--- Day 7: Some Assembly Required ---

This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is a little under the recommended age range, and he needs help assembling the circuit.

Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). A signal is provided to each wire by a gate, another wire, or some specific value. Each wire can only get a signal from one source, but can provide its signal to multiple destinations. A gate provides no signal until all of its inputs have a signal.

The included instructions booklet describes how to connect the parts together: x AND y -> z means to connect wires x and y to an AND gate, and then connect its output to wire z.

For example:

123 -> x means that the signal 123 is provided to wire x.
x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If, for some reason, you'd like to emulate the circuit instead, almost all programming languages (for example, C, JavaScript, or Python) provide operators for these gates.

For example, here is a simple circuit:

123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
After it is run, these are the signals on the wires:

d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?

--- Part Two ---

Now, take the signal you got on wire a, override wire b to that signal, and reset the other wires (including wire a). What new signal is ultimately provided to wire a?
=end

class Wire
  attr_accessor :name, :value, :inputs, :op

  def initialize(line)
    parts = line.split(" ")
    @name = parts.last
    if parts.size == 3
      if parts[0] =~ /^[0-9]+$/
	@value = parts[0].to_i
      else
        @inputs = [parts[0]]
      end
    elsif parts.size == 4
      @inputs = [parts[1]]
      @op = parts[0].to_sym
    elsif parts.size == 5
      @inputs = [parts[0], parts[2]]
      @op = parts[1].to_sym
    end
  end

  def has_value?
    return ! @value.nil?
  end

  def update(other_wire)
    @inputs.each_with_index do |wire_name, index|
      if wire_name == other_wire.name
        @inputs[index] = other_wire.value
      end
    end
  end

  def resolvable?
    return true if has_value?
    @inputs.each do |input|
      return false if input =~ /[a-z]/
    end
    return true
  end

  def resolve
    return if @value
    case @op
      when :RSHIFT
        @value = @inputs[0].to_i >> @inputs[1].to_i
      when :LSHIFT
	@value = @inputs[0].to_i << @inputs[1].to_i
      when :NOT
	@value = 65535 - @inputs[0].to_i
      when :AND
	@value = @inputs[0].to_i & @inputs[1].to_i
      when :OR
	@value = @inputs[0].to_i | @inputs[1].to_i
      else
	@value = @inputs[0].to_i
    end
  end
end

# wire.name => wire
wires = {}
# wire.name => [wires that have this wire as input]
to_resolve = {}
# list of wires that need to be updated
resolved = []
File.open("input") do |f|
  f.each_line do |line|
    wire = Wire.new(line)
    # hacky hack
    wire = Wire.new("3176 -> b") if wire.name == "b"
    wires[wire.name] = wire
    if wire.resolvable?
      resolved << wire
    else
      wire.inputs.each do |input|
	next if input =~ /[0-9]/
	to_resolve[input] ||= []
	to_resolve[input] << wire
      end
    end
  end
end

while not resolved.empty?
  wire = resolved.pop
  # skip things that aren't used as inputs
  next unless to_resolve[wire.name]
  to_resolve[wire.name].each do |r|
    r.update(wire)
    r.resolve && resolved.push(r) if r.resolvable?
  end
end

puts wires["a"].value
