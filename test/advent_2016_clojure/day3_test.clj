(ns advent-2016-clojure.day3-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day3 :refer :all]))

(def TEST_INPUT "101 301  501\n 102 302 502\n103 303  503\n  201  401 601\n202 402 602\n203 403 603")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day3-data.txt"))

(deftest is-triangle-test
  (is (true? (is-triangle? '(3 4 5))))
  (is (false? (is-triangle? '(5 10 25)))))

(deftest part1-test
  (is (= 993 (part1 PUZZLE_INPUT))))

(deftest grouping-test
  (testing "by rows (part 1)"
    (is (= '((101 301 501) (102 302 502) (103 303 503) (201 401 601) (202 402 602) (203 403 603))
           (by-rows (parse-to-int-seq TEST_INPUT)))))
  (testing "by cols (part 2)"
    (is (= '((101 102 103) (201 202 203) (301 302 303) (401 402 403) (501 502 503) (601 602 603))
           (by-cols (parse-to-int-seq TEST_INPUT))))))

(deftest part2-test
  (is (= 1849 (part2 PUZZLE_INPUT))))