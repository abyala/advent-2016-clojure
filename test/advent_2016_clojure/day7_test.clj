(ns advent-2016-clojure.day7-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day7 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day7-data.txt"))

(deftest is-abba-test
  (is (is-abba? "abba"))
  (is (not (is-abba? "aaaa")))
  (is (is-abba? "ioxxoj"))
  (is (is-abba? "ioxxo"))
  (is (not (is-abba? "ioxxxxo"))))

(deftest supports-tls-test
  (is (supports-tls? "abba[mnop]qrst"))
  (is (not (supports-tls? "abcd[bddb]xyyx")))
  (is (not (supports-tls? "aaaa[qwer]tyui")))
  (is (supports-tls? "ioxxoj[asdfgh]zxcvbn")))

(deftest part1-test
  (testing "Sample input"
    (is (= 2
           (part1 "abba[mnop]qrst\nabcd[bddb]xyyx\naaaa[qwer]tyui\nioxxoj[asdfgh]zxcvbn"))))
  (testing "Puzzle input"
    (is (= 110 (part1 PUZZLE_INPUT)))))
