(ns advent-2016-clojure.day02
  (:require [clojure.string :as str]
            [advent-2016-clojure.utils :refer [abs]]))

(def normal-keypad
  {1 {\R 2 \D 4}
   2 {\L 1 \R 3 \D 5}
   3 {\L 2 \D 6}
   4 {\R 5 \U 1 \D 7}
   5 {\L 4 \R 6 \U 2 \D 8}
   6 {\L 5 \U 3 \D 9}
   7 {\R 8 \U 4}
   8 {\L 7 \R 9 \U 5}
   9 {\L 8 \U 6}})

(def diamond-keypad
  {1  {\D 3}
   2  {\R 3 \D 6}
   3  {\U 1 \L 2 \R 4 \D 7}
   4  {\L 3 \D 8}
   5  {\R 6}
   6  {\L 5 \R 7 \U 2 \D \A}
   7  {\L 6 \R 8 \U 3 \D \B}
   8  {\L 7 \R 9 \U 4 \D \C}
   9  {\L 8}
   \A {\R \B \U 6}
   \B {\L \A \R \C \U 7 \D \D}
   \C {\L \B \U 8}
   \D {\U \B}})

(defn move [keypad button direction]
  (or ((keypad button) direction)
      button))

(defn next-button [keypad start instructions]
  (reduce #(move keypad %1 %2) start (vec instructions)))

(defn calculate-code [keypad input]
  (let [lines (str/split-lines input)
        final-state (reduce (fn [{:keys [start codes]} line]
                              (let [next (next-button keypad start line)]
                                {:start next :codes (str codes next)}))
                            {:start 5 :codes ""}
                            lines)]
    (:codes final-state)))

(defn part1 [input]
  (calculate-code normal-keypad input))

(defn part2 [input]
  (calculate-code diamond-keypad input))