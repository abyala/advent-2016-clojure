(ns advent-2016-clojure.day25-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day25 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day25-data.txt"))

(deftest part1-test
  (is (= 192 (part1 PUZZLE_INPUT))))