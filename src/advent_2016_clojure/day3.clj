(ns advent-2016-clojure.day3
  (:require [clojure.string :as str]))

(defn parse-to-int-seq [input]
  (map #(Integer/parseInt %)
       (-> input str/trim (str/split #"\W+"))))

(defn is-triangle? [sides]
  (let [[small2 largest] (map #(reduce + %)
                              (partition-all 2 (sort sides)))]
    (> small2 largest)))

(defn by-rows [int-seq] (partition 3 int-seq))
(defn by-cols [int-seq]
  (->> int-seq (partition 3) (apply interleave) (partition 3)))

(defn count-triangles [grouping-fun input]
  (->> input
       parse-to-int-seq
       grouping-fun
       (filter is-triangle?)
       count))

(defn part2 [input]
  (count-triangles by-cols input))