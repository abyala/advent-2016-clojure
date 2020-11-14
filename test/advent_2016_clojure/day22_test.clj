(ns advent-2016-clojure.day22-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day22 :refer :all]))

(def TEST_INPUT "swap position 4 with position 0\nswap letter d with letter b\nreverse positions 0 through 4\nrotate left 1 step\nmove position 1 to position 4\nmove position 3 to position 0\nrotate based on position of letter b\nrotate based on position of letter d")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day22-data.txt"))

(deftest part1-test
  (is (= 937 (part1 PUZZLE_INPUT))))

(deftest used-disk-test
  (is (true? (used-disk? {:x 1 :y 2 :size 5 :used 4 :avail 1})))
  (is (false? (used-disk? {:x 1 :y 2 :size 3 :used 0 :avail 3}))))

(deftest can-transfer-test
  (is (true? (can-transfer? {:x 1 :y 2 :size 5 :used 4 :avail 1}
                            {:x 2 :y 3 :size 10 :used 6 :avail 4})))
  (is (false? (can-transfer? {:x 1 :y 2 :size 5 :used 4 :avail 1}
                            {:x 2 :y 3 :size 10 :used 7 :avail 3}))))

(deftest eligible-pairs-test
  (let [p1 {:x 1 :y 2 :size 5 :used 4 :avail 1}
        p2 {:x 2 :y 3 :size 6 :used 0 :avail 6}
        p3 {:x 3 :y 4 :size 7 :used 4 :avail 3}]
    (is (= (list [p1 p2] [p3 p2])
           (eligible-pairs (list p1 p2 p3))))))

(deftest neighbor-test
  (is (true? (neighbor? {:x 1 :y 2} {:x 0 :y 2})))
  (is (true? (neighbor? {:x 1 :y 2} {:x 1 :y 1})))
  (is (false? (neighbor? {:x 1 :y 2} {:x 0 :y 1}))))

(deftest coords-of-test
  (is (= [2 3] (coords-of {:x 2 :y 3 :size 5 :used 4 :avail 1}))))

(deftest eligible-moves-test
  (let [p1 {:x 1 :y 2 :size 5 :used 4 :avail 1}
        p2 {:x 2 :y 3 :size 6 :used 0 :avail 6}
        p3 {:x 2 :y 4 :size 7 :used 4 :avail 3}]
    (is (= (list [[2 4] [2 3]])
           (eligible-moves (list p1 p2 p3))))))

(deftest parse-line-test
  (is (= {:x 1 :y 2 :size 5 :used 4 :avail 1}
         (parse-line "/dev/grid/node-x1-y2   5T    4T     1T   80%"))))
