(ns advent-2016-clojure.day19-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day19 :refer :all]))

(def PUZZLE_VALUE 3017957)

(deftest part1-test
  (is (= 3 (part1 5)))
  (is (= 1841611 (part1 PUZZLE_VALUE))))

; Part 2 TOO LOW:  1420678
(deftest part2-test
  (is (= 2 (part2 5)))
  (is (= 1423634 (part2 PUZZLE_VALUE))))
