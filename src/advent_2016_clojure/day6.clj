(ns advent-2016-clojure.day6
  (:require [clojure.string :as str]))

(defn most-common-per-index [words]
  (->> words
       (mapcat #(map-indexed (fn [i v] [i v]) %))
       (group-by first)
       (map (fn [[_ v]]
              (map #(second %) v)))
       (map frequencies)
       (map #(sort-by second %))
       (map last)
       (map first)
       (apply str)))

(defn part1 [input]
  (most-common-per-index (str/split-lines input)))