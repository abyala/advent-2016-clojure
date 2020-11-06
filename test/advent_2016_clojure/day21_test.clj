(ns advent-2016-clojure.day21-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day21 :refer :all]))

(def TEST_INPUT "swap position 4 with position 0\nswap letter d with letter b\nreverse positions 0 through 4\nrotate left 1 step\nmove position 1 to position 4\nmove position 3 to position 0\nrotate based on position of letter b\nrotate based on position of letter d")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day21-data.txt"))

(deftest part1-test
  (is (= "decab" (part1 TEST_INPUT "abcde")))
  (is (= "bdfhgeca" (part1 PUZZLE_INPUT "abcdefgh"))))