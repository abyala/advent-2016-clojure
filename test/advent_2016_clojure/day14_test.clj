(ns advent-2016-clojure.day14-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day14 :refer :all]))

(def PUZZLE_INPUT "zpqevtbw")

(deftest salted-hashes-test
  (is (= '("577571be4de9dcce85a041ba0410f29f" "23734cd52ad4a4fb877d8a1e26e5df5f")
         (take 2 (salted-hashes "abc")))))

(deftest repeats-of-length-test
  (is (= #{} (repeats-of-length 3 "aabbaabbccabc")))
  (is (= #{\a} (repeats-of-length 3 "aabbaaabbccabc")))
  (is (= #{\a} (repeats-of-length 3 "aabbaaabbccaaabc")))
  (is (= #{\a \b} (repeats-of-length 3 "aabbaaabbccaaabbb")))
  (is (= #{} (repeats-of-length 5 "aaaabbbccccdd")))
  (is (= #{\c} (repeats-of-length 5 "aaaabbbcccccdd")))
  )

(deftest repeat-tuple-test
  (is (= [4 #{} #{}] (repeat-tuple 4 "asdfasdfasdfasdf")))
  (is (= [4 #{\a} #{}] (repeat-tuple 4 "asdfaaasdfasdfasdf")))
  (is (= [4 #{\a \b} #{\b}] (repeat-tuple 4 "asdfaaasdfasdfbbbbbasdf"))))

(deftest key?-test
  (is (true? (key? #{\a} [[0 #{} #{}] [1 #{\a} #{\a}]])))
  (is (true? (key? #{\a} [[0 #{} #{}] [1 #{\a \b} #{\a}]])))
  (is (true? (key? #{\a \b} [[0 #{} #{}] [1 #{\a \b} #{\a}]])))
  (is (false? (key? #{} [[0 #{} #{}] [1 #{\a \b} #{\a}]])))
  (is (false? (key? #{\b} [[0 #{} #{}] [1 #{\a \b} #{\a}]]))))

(deftest windowed-hashes-test
  (is (= '(39 92) (take 2 (windowed-hashes "abc")))))

(deftest nth-hash-test
  (is (= 39 (nth-hash 1 "abc")))
  (is (= 92 (nth-hash 2 "abc"))))

(deftest part1-test
  (is (= 22728 (part1 "abc")))
  (is (= 0 (part1 PUZZLE_INPUT))))

; NOT 14212
