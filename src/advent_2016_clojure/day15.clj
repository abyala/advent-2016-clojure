(ns advent-2016-clojure.day15
  (:require [clojure.string :as str]))

(defn create-disk [num-positions starting-position]
  (drop starting-position (cycle (range num-positions))))

(defn seq-of-offset-disks [disks]
  (map-indexed #(vector %1 %2)
               (partition (count disks) (apply interleave disks))))

(defn index-of-all-zeros [offset-disks]
  (keep (fn [[idx positions]]
          (when (every? #(= % 0) positions) idx))
        offset-disks))

(defn parse-disk [line]
  (let [[_ num-positions starting-position] (re-matches #"Disc #\d+ has (\d+) positions; at time=0, it is at position (\d+)." line)]
    (create-disk (Integer/parseInt num-positions) (Integer/parseInt starting-position))))

(defn conj-if-present [x vec]
  (if (some? x) (conj vec x) vec))

(defn solve [input extra-disk]
  (->> input
       str/split-lines
       (mapv parse-disk)
       (conj-if-present extra-disk)
       (map-indexed #(drop %1 %2))
       seq-of-offset-disks
       index-of-all-zeros
       first
       dec))

(defn part1 [input]
  (solve input nil))

(defn part2 [input]
  (solve input (create-disk 11 0)))