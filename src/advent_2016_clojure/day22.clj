(ns advent-2016-clojure.day22
  (:require [clojure.string :as str]))

(defn used-disk? [{used :used}] (pos? used))
(defn can-transfer? [{src :used} {dest :avail}] (>= dest src))

(defn eligible-pairs [disks]
  (for [a (filter used-disk? disks)
        b (filter #(and (not= a %)
                        (can-transfer? a %))
                  disks)]
    [a b]))

(defn coords-of [{x :x y :y}] [x y])

(defn parse-line [line]
  (when-let [tokens (re-matches #"/dev/grid/node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T\s+(\d+)T\s+(\d+)\%" line)]
    (let [[x y size used avail _] (->> (drop 1 tokens)
                                       (map #(Integer/parseInt %)))]
      {:x x :y y :size size :used used :avail avail})))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map parse-line)
       (keep identity)))

(defn part1 [input]
  (->> (parse-input input)
       eligible-pairs
       count))

(defn starting-coords [disks]
  (->> (map coords-of disks)
       (filter #(zero? (second %)))
       sort
       last))

(defn find-empty-disk [state]
  (first (filter #(not (used-disk? %)) state)))

(defn first-x-of-wall [state]
  (->> (filter #(> (:used %) 100) state)
       (map :x)
       (apply min)))

; Don't even get me started. This was a gimmick.
(defn incredibly-irritating-part-2 [input]
  (let [state (parse-input input)
        [target-x _] (starting-coords state)
        wall-x (first-x-of-wall state)
        {empty-x :x empty-y :y} (find-empty-disk state)]
    ; Add together:
    ; - Steps to move the empty block to the start of the wall
    ; - Then one step past to clear the wall
    ; - Then all the way to the top
    ; - Then next to the target data
    ; - Then swap it with the target data itself
    ; - Then 5 steps for each step to move the hole around the data, and then swap it
    (+ (- empty-x wall-x)
       1
       empty-y
       (- target-x wall-x)
       1
       (* 5 (dec target-x)))))
