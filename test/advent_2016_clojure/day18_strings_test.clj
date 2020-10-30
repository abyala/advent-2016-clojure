(ns advent-2016-clojure.day18-strings-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day18_strings :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day18-data.txt"))

(deftest test-part1
  (is (= 6 (num-safe-tiles "..^^." 3)))
  (is (= 38 (num-safe-tiles ".^^.^.^^^^" 10)))
  (is (= 1974 (num-safe-tiles PUZZLE_INPUT 40))))

(deftest test-part2
  (is (= 19991126 (num-safe-tiles PUZZLE_INPUT 400000))))