(ns advent-2016-clojure.day15-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day15 :refer :all]))

(def TEST_INPUT "Disc #1 has 5 positions; at time=0, it is at position 4.\nDisc #2 has 2 positions; at time=0, it is at position 1.")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day15-data.txt"))

(deftest part1-test
  (is (= 5 (part1 TEST_INPUT)))
  (is (= 400589 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 3045959 (part2 PUZZLE_INPUT))))