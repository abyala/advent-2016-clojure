(ns advent-2016-clojure.day2
  (:require [clojure.string :as str]
            [advent-2016-clojure.utils :refer [abs]]))

(defn move [button direction]
  (or
    (case direction
      \U (when (> button 3) (- button 3))
      \D (when (< button 7) (+ button 3))
      \L (when (not= (mod button 3) 1) (dec button))
      \R (when (not= (mod button 3) 0) (inc button)))
    button))

(defn next-code [start instructions]
  (reduce #(move %1 %2) start (vec instructions)))

(defn part1 [input]
  (let [lines (str/split-lines input)
        final-state (reduce (fn [{start :start codes :codes} line]
                              (let [next (next-code start line)]
                                {:start next :codes (str codes next)}))
                            {:start 5 :codes ""}
                            lines)]
    (:codes final-state)))