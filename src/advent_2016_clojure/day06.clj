(ns advent-2016-clojure.day06
  (:require [clojure.string :as str]))

(defn most-common-per-index [first-or-last words]
  (->> words
       (mapcat #(map-indexed (fn [i v] [i v]) %))
       (group-by first)
       (map (fn [[_ v]]
              (map #(second %) v)))
       (map frequencies)
       (map #(sort-by second %))
       (map first-or-last)
       (map first)
       (apply str)))

(defn part1 [input]
  (most-common-per-index last (str/split-lines input)))

(defn part2 [input]
  (most-common-per-index first (str/split-lines input)))