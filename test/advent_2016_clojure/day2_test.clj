(ns advent-2016-clojure.day2-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day2 :refer :all]))

(def TEST_INPUT "ULL\nRRDDD\nLURDL\nUUUUD")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day2-data.txt"))

(deftest move-test
  (let [directions '(\U \D \L \R)]
    (testing "normal keypad"
      (is (= '(1 4 1 2)
             (map #(move normal-keypad 1 %) directions)))
      (is (= '(2 8 4 6)
             (map #(move normal-keypad 5 %) directions)))
      (is (= '(6 9 8 9)
             (map #(move normal-keypad 9 %) directions))))
    (testing "diamond keypad"
      (is (= '(2 6 2 3)
             (map #(move diamond-keypad 2 %) directions)))
      (is (= '(3 \B 6 8)
             (map #(move diamond-keypad 7 %) directions)))
      (is (= '(8 \C \B \C)
             (map #(move diamond-keypad \C %) directions))))))

(deftest next-button-test
  (testing "normal keypad"
    (is (= 1 (next-button normal-keypad 5 "ULL")))
    (is (= 9 (next-button normal-keypad 1 "RRDDD")))
    (is (= 8 (next-button normal-keypad 9 "LURDL")))
    (is (= 5 (next-button normal-keypad 8 "UUUUD"))))
  (testing "diamond keypad"
    (is (= 5 (next-button diamond-keypad 5 "ULL")))
    (is (= \D (next-button diamond-keypad 5 "RRDDD")))
    (is (= \B (next-button diamond-keypad \D "LURDL")))
    (is (= 3 (next-button diamond-keypad \B "UUUUD")))))

(deftest part1-test
  (is (= "1985" (part1 TEST_INPUT)))
  (is (= "74921" (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= "5DB3" (part2 TEST_INPUT)))
  (is (= "A6B35" (part2 PUZZLE_INPUT))))