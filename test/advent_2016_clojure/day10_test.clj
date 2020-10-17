(ns advent-2016-clojure.day10-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day10 :refer :all]))

(def TEST_INPUT "value 5 goes to bot 2\nbot 2 gives low to bot 1 and high to bot 0\nvalue 3 goes to bot 1\nbot 1 gives low to output 1 and high to bot 0\nbot 0 gives low to output 2 and high to output 0\nvalue 2 goes to bot 2")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day10-data.txt"))

(deftest part1-test
  (testing "Test input"
    (is (= 2 (part1 TEST_INPUT 2 5)))
    (is (= 2 (part1 TEST_INPUT 5 2))))
  (testing "Puzzle input"
    (is (= 56 (part1 PUZZLE_INPUT 61 17)))))

(deftest part2-test
  (testing "Test input"
    (is (= 30 (part2 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= 7847 (part2 PUZZLE_INPUT)))))