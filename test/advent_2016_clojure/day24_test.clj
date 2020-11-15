(ns advent-2016-clojure.day24-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day24 :refer :all]))

(def TEST_INPUT "###########\n#0.1.....2#\n#.#######.#\n#4.......3#\n###########")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day24-data.txt"))

(deftest part1-test
  (is (= 14 (part1 TEST_INPUT)))
  (is (= 448 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 672 (part2 PUZZLE_INPUT))))