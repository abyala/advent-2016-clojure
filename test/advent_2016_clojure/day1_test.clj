(ns advent-2016-clojure.day1-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day1 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day1-data.txt"))

(deftest parse-test
  (testing "Single line"
    (is (= [\R 14]
           (parse-move "R14"))))
  (testing "Multi-line"
    (is (= '([\R 14] [\L 3])
           (parse-input "R14, L3")))))

(deftest turn-test
  (testing "Starting from north"
    (let [start {:dir :north :loc [3 4]}]
      (is (= {:dir :east :loc [3 4]}
             (turn start \R)))
      (is (= {:dir :west :loc [3 4]}
             (turn start \L)))))

  (testing "Starting from west"
    (let [start {:dir :west :loc [3 4]}]
      (is (= {:dir :north :loc [3 4]}
             (turn start \R)))
      (is (= {:dir :south :loc [3 4]}
             (turn start \L))))))

(deftest part1-test
  (testing "Sample inputs"
    (is (= 5
           (part1 "R2, L3")))
    (is (= 2
           (part1 "R2, R2, R2")))
    (is (= 12
           (part1 "R5, L5, R5, R3"))))
  (testing "Real input"
    (is (= 252 (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (testing "Sample input"
    (is (= 4 (part2 "R8, R4, R4, R8"))))
  (testing "Puzzle input"
    (is (= 143 (part2 PUZZLE_INPUT)))))