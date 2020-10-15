(ns advent-2016-clojure.day9-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day9 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day9-data.txt"))

(deftest part1-test
  (testing "Test input"
    (is (= 6 (part1 "ADVENT")))
    (is (= 7 (part1 "A(1x5)BC")))
    (is (= 9 (part1 "(3x3)XYZ")))
    (is (= 11 (part1 "A(2x2)BCD(2x2)EFG")))
    (is (= 6 (part1 "(6x1)(1x3)A")))
    (is (= 18 (part1 "X(8x2)(3x3)ABCY")))
    )
  (testing "Puzzle input"
    (is (= 70186 (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (testing "Test input"
    (is (= 9 (part2 "(3x3)XYZ")))
    (is (= 20 (part2 "X(8x2)(3x3)ABCY")))
    (is (= 241920 (part2 "(27x12)(20x12)(13x14)(7x10)(1x12)A")))
    (is (= 445 (part2 "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")))))