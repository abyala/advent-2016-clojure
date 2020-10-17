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
  
* Day 2
  * It's a small thing, but using `or (...) button` in the `move` method allowed decoupling the adjacencies of buttons
  in each keypad from the business logic when a move is not possible. 
  * Focused on using `reduce` instead of `loop-recur` twice.
  * Clojure's on-demand data structures made using `reduce` in `calculate-code` simple. The accumulator
   was a map of the starting digit (whatever button was last pressed) and the digits seen so far.

* Day 3
  * I like how switching the groupings from rows to columns works in the `by-cols` function using the
  `interleave` function.

* Day 4
  * Only special thing is how much I'm using `->` and `->>` now. It definitely reduces eye strain.

* Day 5
  * From reading online, I see that applying a typehint (`[^String s]`) saves a lot of time by avoiding reflection.
  * From experimentation, I see that caching the `MessageDigest` and calling `.reset` actually _increased_ the total
  running time. So this looks less efficient, but it's both faster and thread-safe! 
  * Originally, one function filtered hashes and concatenated them together, but the `valid-hashes` has a distinct
  construct makes this easier to reason with.
  * The `build-password` function looks obscene now, I know, but look at how pretty `part1` and `part2` look! Once
  again, `reduced` short-circuits the `reduce` function, allowing the safe use of the infinite sequence input.
  * The `part2` function leverages `merge` applied to two maps, where the right-most version wins. Hence putting the
  new data as the first argument and the existing map as the allows us to keep the first mapping for each index.
  * I originally was reducing over a String of `........` instead of a map of `{}`, but the map was more interesting.
  
* Day 6
  * I'm not proud of this one. The problem was uninteresting, so I just did list manipulation in the REPL.  *yawn*
  
* Day 7
  * Nothing special

* Day 8
  * I suppose the only thing I learned was the `run!` command, which is like Kotlin's `forEach` function.
  I use it here to get side effects (`println`).
  * I couldn't find a nice way to merge `rotate-col` and `rotate-row`, but I'm rather pleased at how the 
  `parse-instruction` function iterates over potential regex patterns, returning a common partial function.

* Day 9
  * I loved this puzzle and how incredibly concise the answer is!
  * The fact that the `decompress` function takes itself in as a request parameter, and that the recurseive call
  calls itself with itself again was a little trippy.  I kept trying to find a way do do this with partial functions,
  such that `part1` would only have to pass in the simple `count` function, but I don't think that can work due to the
  difference in arity between both functions.  The recursive call has to take itself in to avoid unraveling the
  argument list. 
  
* Day 10
  * Nothing very complex, but it was a fun lesson anyway.
  * I'm starting to make more use of `if-let` and `when-let`, as well as `keep` instead of `(map (filter (...)))`.
  * I did consider attempting this with core.async, and might try that later, but it wasn't necessary.