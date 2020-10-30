(ns advent-2016-clojure.day18-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day18 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day18-data.txt"))

(deftest next-line-test
  (is (= [false true true true true]
         (next-line [false false true true false])))
  (is (= [true true false false true]
         (next-line [false true true true true]))))

(deftest test-trap-sequence
  (is (= (list [false true true false true false true true true true]
               [true true true false false false true false false true]
               [true false true true false true false true true false])
         (take 3 (trap-sequence [false true true false true false true true true true])))))

(deftest test-part1
  (is (= 6 (num-safe-tiles "..^^." 3)))
  (is (= 38 (num-safe-tiles ".^^.^.^^^^" 10)))
  (is (= 1974 (num-safe-tiles PUZZLE_INPUT 40))))

(deftest test-part2
  (is (= 19991126 (num-safe-tiles PUZZLE_INPUT 400000))))