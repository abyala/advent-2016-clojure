(ns advent-2016-clojure.day09)

(defn decompress
  "Takes in a function to apply to the matching compressed characters of the input string."
  [f input]
  (if-let [[_ prefix num-chars num-repeats suffix]
           (re-matches #"([^\(]*)\((\d+)x(\d+)\)(.*)" input)]

    ; Any prefix, (apply f to num-chars)*num-repeats, then repeat on suffix
    (let [[repeating leftovers] (map #(apply str %)
                                     (split-at (Integer/parseInt num-chars) suffix))]
      (+ (count prefix)
         (* (f f repeating) (Integer/parseInt num-repeats))
         (decompress f leftovers)))
    (count input)))

(defn part1 [input] (decompress #(count %2) input))
(defn part2 [input] (decompress decompress input))