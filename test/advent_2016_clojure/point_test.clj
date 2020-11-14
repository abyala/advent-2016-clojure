(ns advent-2016-clojure.point-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.point :refer :all]))

(deftest add-test
  (is (= [3 4] (add [1 2] [2 2])))
  (is (= [3 4] (add [3 4] [0 0]))))

(deftest distance-test
  (is (= 4 (distance [1 2] [3 4])))
  (is (= 4 (distance [3 4] [1 2])))
  (is (= 0 (distance [0 0])))
  (is (= 5 (distance [3 2]))))