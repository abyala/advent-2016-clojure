(ns advent-2016-clojure.day3-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day3 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day3-data.txt"))

(deftest is-triangle-test
  (is (true? (is-triangle? '(3 4 5))))
  (is (false? (is-triangle? '(5 10 25)))))

(deftest part1-test
  (is (= 993 (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= 1849 (part2 PUZZLE_INPUT))))