(ns advent-2016-clojure.day5-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day5 :refer :all]))

(def PUZZLE_INPUT "cxdnnyjw")

(deftest part1-test
  (testing "Sample input"
    (is (= "18f47a30" (part1 "abc"))))
  (testing "Puzzle input"
    (is (= "f77a0e6e" (part1 PUZZLE_INPUT))))
  )