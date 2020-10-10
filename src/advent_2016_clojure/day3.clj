(ns advent-2016-clojure.day3
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (map #(Integer/parseInt %)
       (-> line str/trim (str/split #" +"))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn is-triangle? [sides]
  (let [[a b c] (sort sides)]
    (> (+ a b) c)))

(def by-rows identity)
(defn by-cols [int-seq]
  (->> int-seq (apply interleave) (partition 3)))

(defn count-triangles [grouping-fun input]
  (->> input
       parse-input
       grouping-fun
       (filter is-triangle?)
       count))

(defn part1 [input]
  (count-triangles by-rows input))

(defn part2 [input]
  (count-triangles by-cols input))