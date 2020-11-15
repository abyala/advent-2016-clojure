(ns advent-2016-clojure.assembunny-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.assembunny :refer :all]))

(def TEST_INPUT "cpy 2 a\ntgl a\ntgl a\ntgl a\ncpy 1 a\ndec a\ndec a")

(deftest toggle-test
  (testing "one-arg instructions"
    (is (= [["dec" "b"]]
           (toggle "0" empty-registers [["inc" "b"]] 0)))
    (is (= [["foo" "bar"] ["inc" "c"]]
           (toggle "0" empty-registers [["foo" "bar"] ["dec" "c"]] 1)))
    (is (= [["inc" "5"]]
           (toggle "0" empty-registers [["tgl" "5"]] 0))))
  (testing "two-arg instructions"
    (is (= [["jnz" "2" "a"]]
           (toggle "0" empty-registers [["cpy" "2" "a"]] 0)))
    (is (= [["cpy" "a" "b"]]
           (toggle "0" empty-registers [["jnz" "a" "b"]] 0))))
  (testing "x is numeric"
    (is (= [["inc" "a"] ["inc" "b"] ["dec" "c"]]
           (toggle "1" empty-registers [["inc" "a"] ["inc" "b"] ["inc" "c"]] 1))))
  (testing "x is a register"
    (is (= [["inc" "a"] ["inc" "b"] ["dec" "c"]]
           (toggle "a" {"a" 2} [["inc" "a"] ["inc" "b"] ["inc" "c"]] 0)))))

(deftest apply-instruction-test
  (is (= {:registers empty-registers :jump-by 1
          :instructions [["cpy" "2" "a"]
                         ["tgl" "a"]
                         ["inc" "a"]
                         ["tgl" "a"]
                         ["cpy" "1" "a"]
                         ["dec" "a"]
                         ["dec" "a"]]}
         (apply-instruction (input-to-instructions TEST_INPUT) 2 empty-registers))))
