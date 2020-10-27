(ns advent-2016-clojure.day16-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day16 :refer :all]))

(def TEST_INPUT "Mew")
(def PUZZLE_INPUT "10001110011110000")

(deftest dragon-curve-test
  (is (= "100" (dragon-curve "1")))
  (is (= "001" (dragon-curve "0")))
  (is (= "11111000000" (dragon-curve "11111")))
  (is (= "1111000010100101011110000" (dragon-curve "111100001010"))))

(deftest checksum-test
  (is (= "100" (checksum "110010110100"))))

(deftest fill-disk-test
  (is (= "10000011110010000111" (fill-disk "10000" 20)))
  (is (= "01100" (checksum (fill-disk "10000" 20)))))

(deftest part1-test
  (is (= "10010101010011101" (part1 PUZZLE_INPUT))))

(deftest part2-test
  (is (= "01100111101101111" (part2 PUZZLE_INPUT))))