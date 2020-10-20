(ns advent-2016-clojure.day11-rewrite-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day11_rewrite :refer :all]))

(def TEST_INPUT "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\nThe second floor contains a hydrogen generator.\nThe third floor contains a lithium generator.\nThe fourth floor contains nothing relevant.")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day11-data.txt"))

(deftest parse-line-test
  (is (= '([{:element "hydrogen"} {:chip 1}] [{:element "lithium"} {:chip 1}])
         (parse-line 1 "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.")))
  (is (= ()
         (parse-line 2 "Nothing to parse here.")))
  (is (= '([{:element "blue"} {:chip 3}] [{:element "red"} {:generator 3}])
         (parse-line 3 "There is a blue-compatible microchip and a red generator."))))

(deftest possible-next-floors-test
  (is (= '(2) (possible-next-floors 1)))
  (is (= '(1 3) (possible-next-floors 2)))
  (is (= '(2 4) (possible-next-floors 3)))
  (is (= '(3) (possible-next-floors 4))))

(deftest floors-with-generators-test
  (is (= #{1}
         (floors-with-generators {:elements '({:chip 2 :generator 1} {:chip 3 :generator 1})})))
  (is (= #{2 3}
         (floors-with-generators {:elements '({:chip 1 :generator 2} {:chip 1 :generator 3})}))))

(deftest floors-with-detached-chips-test
  (is (= #{}
         (floors-with-detached-chips {:elements '({:chip 1 :generator 1} {:chip 2 :generator 2})})))
  (is (= #{1}
         (floors-with-detached-chips {:elements '({:chip 1 :generator 2} {:chip 1 :generator 3})})))
  (is (= #{1 2}
         (floors-with-detached-chips {:elements '({:chip 1 :generator 2} {:chip 2 :generator 1})}))))

(deftest part1-test
  (testing "Sample data"
    (is (= 11 (part1 TEST_INPUT))))
  (testing "Puzzle data"
    (is (= 33 (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (testing "Puzzle data"
    (is (= 57 (part2 PUZZLE_INPUT)))))
