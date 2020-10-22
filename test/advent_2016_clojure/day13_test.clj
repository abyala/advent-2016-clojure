(ns advent-2016-clojure.day13-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day13 :refer :all]))

(deftest count-binary-ones-test
  (is (= 0 (count-binary-ones 0)))
  (is (= 1 (count-binary-ones 1)))
  (is (= 1 (count-binary-ones 2)))
  (is (= 2 (count-binary-ones 3)))
  (is (= 3 (count-binary-ones 7)))
  (is (= 1 (count-binary-ones 8))))

(deftest walkable-test
  (is (true? (walkable? 10 [0 0])))
  (is (false? (walkable? 10 [1 0])))
  (is (true? (walkable? 10 [0 1])))
  (is (true? (walkable? 10 [1 1])))
  (is (false? (walkable? 10 [2 1]))))

(deftest walkable-neighbors-test
  (is (= '([1 2] [0 1]) (walkable-neighbors 10 [1 1])))
  (is (= '([7 2] [7 0] [8 1] [6 1]) (walkable-neighbors 10 [7 1])))
  (is (= () (walkable-neighbors 10 [5 3]))))

(deftest a-star-estimate-test
  (is (= 3 (a-star-estimate 3 [5 6] [5 6])))
  (is (= 3 (a-star-estimate 2 [5 6] [4 6]))))

(deftest part1-test
  (is (= 11 (part1 10)))
  (is (= 86 (part1 1364))))