(ns advent-2016-clojure.day10-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day10 :refer :all]))

(def TEST_INPUT "value 5 goes to bot 2\nbot 2 gives low to bot 1 and high to bot 0\nvalue 3 goes to bot 1\nbot 1 gives low to output 1 and high to bot 0\nbot 0 gives low to output 2 and high to output 0\nvalue 2 goes to bot 2")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day10-data.txt"))
;
;(deftest part1-test
;  (testing "Test input"
;    (is (= 6 (part1 "ADVENT")))
;    (is (= 7 (part1 "A(1x5)BC")))
;    (is (= 9 (part1 "(3x3)XYZ")))
;    (is (= 11 (part1 "A(2x2)BCD(2x2)EFG")))
;    (is (= 6 (part1 "(6x1)(1x3)A")))
;    (is (= 18 (part1 "X(8x2)(3x3)ABCY")))
;    )
;  (testing "Puzzle input"
;    (is (= 70186 (part1 PUZZLE_INPUT)))))
;
;(deftest part2-test
;  (testing "Test input"
;    (is (= 9 (part2 "(3x3)XYZ")))
;    (is (= 20 (part2 "X(8x2)(3x3)ABCY")))
;    (is (= 241920 (part2 "(27x12)(20x12)(13x14)(7x10)(1x12)A")))
;    (is (= 445 (part2 "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"))))
;  (testing "Puzzle data"
;    (is (= 10915059201 (part2 PUZZLE_INPUT)))))

;value 11 goes to bot 124


(let [line "value 7 goes to bot 1"
      state {:bots {0 '() 1 '(\a) 2 '(3 5)} #_(init-bots 2) :algs {}}
      {bots :bots algs :algs} state]
  (if-let [[_ val-str bot] (re-matches #"value (\d+) goes to bot (\d+)" line)]
    (update state :bots #(give-chip-to-bot (Integer/parseInt val-str) (Integer/parseInt bot) %))
    (let [[_ & matches] (re-matches #"bot (\d+) gives low to bot (\d+) and high to bot (\d+)" line)
          [bot-a bot-b bot-c] (map #(Integer/parseInt %) matches)]
      (let [f (partial move-chips bot-a bot-b bot-c)]
        (update state :bots f))))
  )