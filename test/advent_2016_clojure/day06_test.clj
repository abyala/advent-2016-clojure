(ns advent-2016-clojure.day06-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day06 :refer :all]))

(def TEST_INPUT "eedadn\ndrvtee\neandsr\nraavrd\natevrs\ntsrnev\nsdttsa\nrasrtv\nnssdts\nntnada\nsvetve\ntesnvt\nvntsnd\nvrdear\ndvrsen\nenarar")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day06-data.txt"))

(deftest part1-test
  (testing "Sample input"
    (is (= "easter" (part1 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= "gebzfnbt" (part1 PUZZLE_INPUT)))))

(deftest part1-test
  (testing "Sample input"
    (is (= "advent" (part2 TEST_INPUT))))
  (testing "Puzzle input"
    (is (= "fykjtwyn" (part2 PUZZLE_INPUT)))))
