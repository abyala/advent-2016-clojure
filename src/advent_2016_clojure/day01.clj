(ns advent-2016-clojure.day01
  (:require [clojure.string :as str]
            [advent-2016-clojure.utils :refer [abs]]))

(defn parse-move
  "Parses a single element of form Lxxx or Rxxx, and returns vector of [dir distance]"
  [input]
  [(first input) (Integer/parseInt (subs input 1))])

(defn parse-input
  "Parses a complete input of comma-and-space separated moves into a collection"
  [input]
  (map parse-move (str/split input #", ")))

; STATE: dir, loc
(def initial-state {:dir :north, :loc [0 0]})

(defn turn
  "Returns the new state of the program after turning to face left or right"
  [state face]
  (assoc state :dir (case [(:dir state) face]
                      [:north \L] :west
                      [:north \R] :east
                      [:south \L] :east
                      [:south \R] :west
                      [:east \L] :north
                      [:east \R] :south
                      [:west \L] :south
                      [:west \R] :north)))

(defn all-steps
  "Returns a collection of all steps taken from an initial state and a step count"
  [state steps]
  (let [loc (:loc state)
        f (case (:dir state)
            :north (fn [[x y]] [x (inc y)])
            :south (fn [[x y]] [x (dec y)])
            :east (fn [[x y]] [(inc x) y])
            :west (fn [[x y]] [(dec x) y]))]
    (drop 1 (reductions (fn [col _] (f col)) loc (range steps)))))

(defn walk-path
  "Returns a lazy collection of all steps taken through a collection of moves"
  ([steps] (walk-path steps initial-state))
  ([[[face dist] & steps] state]
   (when (some? face)
     (let [turned (turn state face)
           next-steps (all-steps turned dist)
           next-state (assoc turned :loc (last next-steps))]
       (lazy-seq (concat next-steps (walk-path steps next-state)))))))

(defn first-repeat
  "Returns the first element of a collection that appears more than once"
  [items]
  (let [answer (reduce (fn [found x]
                         (if (contains? found x) (reduced x) (conj found x)))
                       #{}
                       items)]
    (when (not= answer (set items)) answer)))

(defn street-distance
  "Returns the street/Manhattan distance of a vector of integers"
  [loc]
  (reduce + (map abs loc)))

(defn solve
  "Returns the street distance of applying the function f to the path walked by the input"
  [f input]
  (->> input
       parse-input
       walk-path
       f
       street-distance))

(defn part1 [input] (solve last input))
(defn part2 [input] (solve first-repeat input))
