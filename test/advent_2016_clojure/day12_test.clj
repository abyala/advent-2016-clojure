(ns advent-2016-clojure.day12-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day12 :refer :all]))

(def TEST_INPUT "cpy 41 a\ninc a\ninc a\ndec a\njnz a 2\ndec a")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day12-data.txt"))

(deftest part1-test
  (is (= 42 (part1 TEST_INPUT)))
  (is (= 318083 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 9227737 (part2 PUZZLE_INPUT))))