(ns advent-2016-clojure.day2-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day2 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day2-data.txt"))


(deftest move-test
  (let [directions '(\U \D \L \R)]
    (is (= '(1 4 1 2)
          (map #(move 1 %) directions)))
    (is (= '(2 8 4 6)
           (map #(move 5 %) directions)))
    (is (= '(6 9 8 9)
           (map #(move 9 %) directions)))))

(deftest next-code-test
  (is (= 1 (next-code 5 "ULL")))
  (is (= 9 (next-code 1 "RRDDD")))
  (is (= 8 (next-code 9 "LURDL")))
  (is (= 5 (next-code 8 "UUUUD"))))

(deftest part1-test
  (is (= "1985" (part1 "ULL\nRRDDD\nLURDL\nUUUUD")))
  (is (= "74921" (part1 PUZZLE_INPUT))))