(ns advent-2016-clojure.day20
  (:require [clojure.string :as str]))

(defn parse-ip-diffs
  "Returns a vector of [id diff] from a structured input string.
  The ID is a non-negative integer, and diff represents how many IP ranges are starting a
  sequence at that value (positive number) or terminating at that value (negative number).
  Pairs are sorted by ID, and will not have a diff of zero."
  [input]
  (->> (str/split input #"[^\d]")
       (keep #(when (not (str/blank? %)) (Long/parseLong %)))
       (map-indexed #(if (even? %1) [%2 1] [(inc %2) -1]))
       (group-by first)
       vals
       (map #(reduce (fn [[n acc] [_ v]] [n (+ acc v)])
                     [(ffirst %) 0]
                     %))
       (filter #(not= (second %) 0))
       (sort-by first)))

(defn blacklist-ranges
  "Returns a vector of pairs of integers that define non-negative integers that are blacklisted.
  The low value is inclusive and the high is exclusive."
  [ip-diffs]
  (-> (reduce (fn [[ranges start depth] [id depth-change]]
                (let [new-depth (+ depth depth-change)]
                  (if (zero? new-depth)
                    [(conj ranges [start id]), nil, new-depth]
                    [ranges (or start id) new-depth])))
              [[] nil 0]  ; [ ranges, starting index, depth ]
              ip-diffs)
      first))

(defn first-whitelisted-value
  "Given a list of blacklist ranges, returns the first non-negative integer to be whitelisted."
  [ranges]
  (let [[a b] (first ranges)]
    (if (zero? a) b 0)))

(defn part1 [input]
  (->> (parse-ip-diffs input)
       blacklist-ranges
       first-whitelisted-value))

(defn part2 [input]
  (->> (parse-ip-diffs input)
       blacklist-ranges
       (map (fn [[a b]] (- b a)))
       (apply +)
       (- 4294967296)))