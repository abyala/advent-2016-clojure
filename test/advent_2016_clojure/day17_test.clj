(ns advent-2016-clojure.day17-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day17 :refer :all]))

(def TEST_INPUT "NOTHING")
(def PUZZLE_INPUT "vwbaicqe")

(deftest door-open-test
  (is (false? (door-open? \0)))
  (is (false? (door-open? \9)))
  (is (false? (door-open? \a)))
  (is (true? (door-open? \b)))
  (is (true? (door-open? \f))))

(deftest open-door-hashes-test
  (is (= #{:up :down :left} (set (open-door-hashes "hijkl" ""))))
  (is (= #{:up :left :right} (set (open-door-hashes "hijkl" "D"))))
  (is (= #{} (set (open-door-hashes "hijkl" "DR"))))
  (is (= #{:right} (set (open-door-hashes "hijkl" "DU")))))

(deftest move-dir-test
  (is (= [3 5] (move-dir [3 4] :up)))
  (is (= [3 3] (move-dir [3 4] :down)))
  (is (= [2 4] (move-dir [3 4] :left)))
  (is (= [4 4] (move-dir [3 4] :right))))

(deftest on-map-test
  (is (true? (on-map? [0 0])))
  (is (true? (on-map? [0 3])))
  (is (true? (on-map? [3 0])))
  (is (false? (on-map? [0 4])))
  (is (false? (on-map? [4 0])))
  (is (false? (on-map? [-1 0])))
  (is (false? (on-map? [0 -1]))))

(deftest next-moves-test
  (is (= (list {:path "D" :dir :down :point [0 2]})
         (next-moves "hijkl" "" [0 3])))
  (is (= (list {:path "DU" :dir :up :point [0 3]}
               {:path "DR" :dir :right :point [1 2]})
         (next-moves "hijkl" "D" [0 2])))
  (is (= ()
         (next-moves "hijkl" "DR" [1 2]))))

(deftest a-star-estimate-test
  (is (= 6 (a-star-estimate {:path "" :point [0 3]})))
  (is (= 8 (a-star-estimate {:path "DU" :point [0 3]})))
  (is (= 0 (a-star-estimate {:path "" :point [3 0]}))))

(deftest part1-test
  (testing "Test data"
    (is (= "DDRRRD" (part1 "ihgpwlah")))
    (is (= "DDUDRLRRUDRD" (part1 "kglvqrro")))
    (is (= "DRURDRUDDLLDLUURRDULRLDUUDDDRR" (part1 "ulqzkmiv"))))
  (testing "Puzzle data"
    (is (= "DRDRULRDRD" (part1 PUZZLE_INPUT)))))