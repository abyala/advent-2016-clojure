# Advent of Code 2016

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
  * Right at the end, I realized I didn't need to initialize my bots.  Calling `(cons 1 '())` is equivalent
  to `(cons 1 nil)`, so I didn't need to map all possible bot values to `'()` or worry about lookups with default
  values. Very nice, Clojure!
  * Get this - I don't need to initialize _anything!_ I removed the `:bots`, `:algs`, and `:outputs` initial mappings
  to empty maps, and it all works.  I can even change `initialize-input` to start with `nil` instead of `{}`! 
  This actually works, but ewww!

* Day 11
  * This one was tough for a few reasons.
  * First, I changes how I wanted to represent the state. Eventually, I settled on 
  `{:elevator n, :floors { floor# ({:type t :element e})}}` made sense to help with choosing possible moves
  and to validate their validity with `safe-state?`.
  * To handle multi-field comparison of maps, I used the `compare` function with vectors of fields. You can see this
  in `possible-pairs` and `state-summary`.
  * I used `as->` when needing to mix and match `->` and `->>`.  Usually, `->` works on elements and `->>` works on
  collections; the fact that I'm using `as->` suggests that perhaps I should make separate functions.
  * The hardest part by far was making `steps-to-move-up` efficient. I realized that states need not be revisited if
  they have the same relationships between chips and generators, but only differ by element. I maintained two separate
  sets, one for available candidates and another for summaries (maps of floor-of-chip and floor-of-generator) to avoid
  duplicates.
  * Now that I think of it, I could possible rewrite this to ONLY deal with summaries. Knowing the elements could be
  helpful if this were a "real" project, but it's not.  Maybe this is an exercise for later in the week.
  * The `re-seq` function is awesome, returning all possible matches for a regex against a string. Super useful.
  * My consolation for working for so long on part 1 was that part 2 was a breeze.
  
* Day 11 (rewrite)
  * Well I rewrote the program, this time with the state being `{:elevator n, :elements ({:chip x :generator y})`.
  * The new data format made it easy to make trivial functions like `floors-with-generators` and 
  `floors-with-detached-chips`, which composed beautifully into `state-state?`. Even `final-state?` looks simple now.
  * I did have to use `keep-indexed` and `map-indexed` to break each element into its constituent pieces again, so I
  suppose that's the trade-off of looking at the data in two different ways.
  * New for me this time was `assoc-in`, which allows changing a value in a map, given a vector of its access path. The
  `move-item-to-floor` was very nice in that I just changed the floor of the `:chip` or `:generator` without doing the 
  remove-and-append algorithm from my original design. This also allowed for a nice `reduce` in the `move-items`
  function.
  * The `solve` function is still a bit hairy, mostly because it manages composing and decomposing the `candidates` map,
  since I need that sorted by `distance` without wanting that to be part of the state itself. But other than that,
  it's a fairly simple solution.   
  * *End result:* The running time dropped in half, from 5.4 seconds to 2.7 seconds. This meets my expectations.

* Day 12
  * Nothing too interesting. I feel like I've done this before, but in fairness I did years 2017-2020 before 2020.
  * Why do I get the sinking feeling that I'll be extending this little computer in future problems?

* Day 13
  * Meh.
  
* Day 14
  * This one stumpted me, and I went all the way to day 25.  Then I reread the instructions, which said to only include
  the first matching triplet, instead of matching any of them.  :shrug:
  * I like the `some` function as the equivalent of the `any` Kotlin function.
  
* Day 15
  * First fun part was the `cycle` function within `create-disk` which is the easiest way to create a repeating
  sequence, in this case of a closed range.
  * The `seq-of-offset-disks` uses the `interleave` function, which takes takes the first of each sequence, 
  then the second of each, etc.  I used the `apply interleave` since it is passed a sequence of sequences, and then
  used `partition` by the number of sequences, such that it generated a tuple of the `nth` value from each
  sequence.  Pair that via `map-indexed` to the index to get the result.  
  
* Day 16
  * Pretty standard problem. Part 2 takes 28 seconds to run, so I'll bet there's some clever algorithm to find that
  makes it run much faster. I might think about it tonight, but my goal is Clojure experience, not necessarily
  super-clever algorithms.
  
* Day 17
  * Standard path algorithm.
  * My original solution for part1 made use of an a-star estimate, to always choose the shortest estimate to the end.
  But when I got to part2 and saw I could reuse the `paths-to-vault` function, sorting didn't provide any value anymore.
  * Odd finding -- when the `paths-to-vault` combines the next moves with the `others`, calling `concat` is roughly
  twice as slow as `into others`.
  * Ok, this may be dumb, but the predicate `(<= 0 x 3)` is about as Clojure-y as you get.
  * `zipmap` is a cute function.  I use to to create a map of directions to values, given a vector of keys
   `[:up :down :left :right]` and a sequence of the corresponding values.
   
* Day 18
  * Not much challenge here.  Maybe this causes a memory constraint if the program didn't use lazy sequences or used
  normal recursion?  This was very straightforward.
  * Well then I looked at 
  [Todd Ginsberg's solution](https://github.com/tginsberg/advent-2016-kotlin/blob/master/src/main/kotlin/com/ginsberg/advent2016/Day18.kt)
   in Kotlin, and made me want to try another implementation where I kept working with Strings instead of converting
   the original input to a vector of booleans. For whatever reason, the running time jumped from 3 seconds to 15. I'm
   not sure if my implementation is off, or if Strings are just that much less efficient than boolean vectors, or if
   Clojure (or Java) is just particularly less efficient at calling `String.charAt`. From what I'm seeing, Clojure's
   RT code does end up calling `String.charAt` after doing a lot of type checks, such as ensuring the variable is
   indexable and eventually a String, the index is numeric, etc. It's a curious finding, whatever it is.

* Day 19
  * The simple algorithm of removing from a vector, or rotating a vector around with each removal, performed too slowly.
  * Part 1 would have been easy with a simple `partition 2`, but that didn't work for part 2.
  * My solution required some spreadsheet work to verify for part 2, but it involved calculating potential thefts using
  just indexes within a array _size_, and then mapping those indexes to the actual elf IDs. To avoid complexity with
  stealing from an index with a lower index, I only allowed forward-steals, and then I used `into` and `split-at` to
  rotate elves who had already stolen to the back of the vector.

* Day 20
  * Nothing crazy.

* Day 21
  * I'll admit I couldn't figure out the secret trick that would make cracking the password work. I see that most
  operations are reversible, but `rotate based on position of letter X` appears to be lossy. So after trying to figure
  out the trick for a while, I settled for a rainbow table attack, and that's just fine for the data set.
  * Special thanks to [Stack Overflow](https://stackoverflow.com/a/26076537) for a nice function to create all
  permutations of a set.
  
* Day 23
  * Once again, there's some clever trick that makes part 2 efficient, but I'm not spending my time on it.
  So instead, this test case completes in somewhere between 1 and 3 hours.
  
* Day 24
  * Really using a lot of lessons throughout this advent.
  * My biggest realization is that I've done a lot of shortest path algorithms; no doubt there is a bunch more reuse
  I could sniff out from previous days if I wanted. But I'm so close to being done!