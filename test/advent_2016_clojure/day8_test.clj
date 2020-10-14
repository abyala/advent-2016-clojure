(ns advent-2016-clojure.day8-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day8 :refer :all]))

(def TEST_INPUT '("rect 3x2" "rotate column x=1 by 1" "rotate row y=0 by 4" "rotate column x=1 by 1"))
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day8-data.txt"))

(deftest run-program-test
  (is (= {:width 8 :height 3 :pixels #{[1 0] [4 0] [6 0] [0 1] [2 1] [1 2]}}
         (run-program 8 3 TEST_INPUT))))

(deftest part1-test
  (testing "Sample data"
    (is (= 6 (part1 "rect 3x2\nrotate column x=1 by 1\nrotate row y=0 by 4\nrotate column x=1 by 1"))))
  (testing "Puzzle data"
    (is (= 121 (part1 PUZZLE_INPUT)))))