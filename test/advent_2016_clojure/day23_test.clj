(ns advent-2016-clojure.day23-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day23 :refer :all]))

(def TEST_INPUT "cpy 2 a\ntgl a\ntgl a\ntgl a\ncpy 1 a\ndec a\ndec a")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day23-data.txt"))

(deftest part1-test
  (is (= 3 (part1 TEST_INPUT)))
  (is (= 12492 (part1 PUZZLE_INPUT))))

; This test case takes well over an hour, if not three. But it works.
(deftest part2-test
  (is (= 479009052 (part2 PUZZLE_INPUT))))

