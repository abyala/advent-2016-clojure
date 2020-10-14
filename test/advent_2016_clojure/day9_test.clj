(ns advent-2016-clojure.day9-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day9 :refer :all]))

;(def TEST_INPUT '("rect 3x2" "rotate column x=1 by 1" "rotate row y=0 by 4" "rotate column x=1 by 1"))
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day9-data.txt"))

(deftest process-datalink-test
  (is (= "ADVENT" (decompress "ADVENT")))
  (is (= "ABBBBBC" (decompress "A(1x5)BC")))
  (is (= "XYZXYZXYZ" (decompress "(3x3)XYZ")))
  (is (= "ABCBCDEFEFG" (decompress "A(2x2)BCD(2x2)EFG")))
  (is (= "(1x3)A" (decompress "(6x1)(1x3)A")))
  (is (= "X(3x3)ABC(3x3)ABCY" (decompress "X(8x2)(3x3)ABCY"))))

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
