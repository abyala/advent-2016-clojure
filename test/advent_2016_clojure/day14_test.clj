(ns advent-2016-clojure.day14-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day14 :refer :all]))

(def PUZZLE_INPUT "zpqevtbw")

(deftest salted-hashes-test
  (is (= '("577571be4de9dcce85a041ba0410f29f" "23734cd52ad4a4fb877d8a1e26e5df5f")
         (take 2 (salted-hashes "abc")))))

(deftest repeats-of-length-test
  (is (= () (repeats-of-length 3 "aabbaabbccabc")))
  (is (= '(\a) (repeats-of-length 3 "aabbaaabbccabc")))
  (is (= '(\a \a) (repeats-of-length 3 "aabbaaabbccaaabc")))
  (is (= '(\a \a \b) (repeats-of-length 3 "aabbaaabbccaaabbb")))
  (is (= () (repeats-of-length 5 "aaaabbbccccdd")))
  (is (= '(\c) (repeats-of-length 5 "aaaabbbcccccdd"))))

(deftest first-triplet-test
  (is (nil? (first-triplet "aabbcc")))
  (is (= \a (first-triplet "aabbaaaccc"))))

(deftest all-quints-test
  (is (= #{} (all-quints "aaaabbbbccccdddefffbb")))
  (is (= #{\a \b} (all-quints "aaabbbbbcccaaaaad"))))

(deftest repeat-tuple-test
  (is (= [4 nil #{}] (repeat-tuple 4 "asdfasdfasdfasdf")))
  (is (= [4 \a #{}] (repeat-tuple 4 "asdfaaasdfasdfasdf")))
  (is (= [4 \a #{\b}] (repeat-tuple 4 "asdfaaasdfasdfbbbbbasdf"))))

(deftest key?-test
  (is (true? (key? \a [[0 nil #{}] [1 \a #{\a}]])))
  (is (true? (key? \a [[0 nil #{}] [1 \b #{\a}]])))
  (is (nil? (key? \b [[0 nil #{}] [1 \b #{\a}]])))
  (is (nil? (key? nil [[0 nil #{}] [1 #{\a \b} #{\a}]])))
  (is (nil? (key? \b [[0 nil #{}] [1 #{\a \b} #{\a}]]))))

(deftest windowed-hashes-test
  (is (= '(39 92) (take 2 (windowed-hashes "abc")))))

(deftest nth-hash-test
  (is (= 39 (nth-hash 1 "abc")))
  (is (= 92 (nth-hash 2 "abc"))))

(deftest part1-test
  (is (= 22728 (part1 "abc")))
  (is (= 16106 (part1 PUZZLE_INPUT))))
