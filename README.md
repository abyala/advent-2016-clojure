# Advent of Code 2017

As part of my learning Clojure, I've moved on to the [2016 Advent of Code](https://adventofcode.com/2016/) problems. 
I'm actually working on this _after_ spending time on 2017, so hopefully this code will be either
a little better, or at least a bit more interesting.

# Areas of Focus / Lessons Learned

* Day 1
  * Single implementation for both parts, returning a list of all steps. The original 
  part 1 used a simple `reduce` function to calculate the last step, but I changed it.
  * The `all-steps` function uses the `reductions` higher-order function, which returns
  every step in a `reduce` function, instead of just the last value.
  * `walk-path` returns a `lazy-seq`, which I've used a few times but not many.
  * `first-repeat` is more flexible than needed, but it should work with any collection.
  * `first-repeat` leverages the `reduced` function, which forces a `reduce` to return
  a single value once it's known. Used as a sort-of short-circuit.
  * Leveraged `->>` in the `solve` function to avoid having to unwrap parentheses.
  I find the readability improvement is greater than I was expecting.
  * How cool is it that part1 and part2 just differ in the functions passed to `solve`?