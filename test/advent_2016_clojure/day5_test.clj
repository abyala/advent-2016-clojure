(ns advent-2016-clojure.day5-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day5 :refer :all]))

(def TEST_INPUT "abc")
(def PUZZLE_INPUT "cxdnnyjw")

(deftest part1-test
  (testing "Sample input"
    (is (= "18f47a30" (part1 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= "f77a0e6e" (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (testing "Sample input"
    (is (= "05ace8e3" (part2 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= "999828ec" (part2 PUZZLE_INPUT)))))