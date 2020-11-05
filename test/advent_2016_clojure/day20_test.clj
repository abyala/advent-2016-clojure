(ns advent-2016-clojure.day20-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day20 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day20-data.txt"))

(deftest part1-test
  (is (= 3 (part1 "5-8\n0-2\n4-7")))
  (is (= 14975795 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 101 (part2 PUZZLE_INPUT))))

