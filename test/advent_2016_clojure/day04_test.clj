(ns advent-2016-clojure.day04-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day04 :refer :all]))

(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day04-data.txt"))

(deftest parse-line-test
  (is (= {:name "a-b-c-d-e-f-g-h" :id 987 :checksum "abcde"}
         (parse-line "a-b-c-d-e-f-g-h-987[abcde]"))))

(deftest room-checksum-test
  (is (= "abxyz" (room-checksum "aaaaa-bbb-z-y-x-123")))
  (is (= "oarel" (room-checksum "not-a-real-room")))
  (is (= "loart" (room-checksum "totally-real-room"))))

(deftest part1-test
  (testing "Test input"
    (is (= 1514
           (part1 "aaaaa-bbb-z-y-x-123[abxyz]\na-b-c-d-e-f-g-h-987[abcde]\nnot-a-real-room-404[oarel]\ntotally-real-room-200[decoy]"))))
  (testing "Puzzle data"
    (is (= 245102 (part1 PUZZLE_INPUT)))))

(deftest part2-test
  (is (= 324 (part2 PUZZLE_INPUT))))