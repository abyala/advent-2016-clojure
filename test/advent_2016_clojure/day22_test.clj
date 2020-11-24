(ns advent-2016-clojure.day22-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day22 :refer :all]))

(def TEST_INPUT "Filesystem            Size  Used  Avail  Use%\n/dev/grid/node-x0-y0   10T    8T     2T   80%\n/dev/grid/node-x0-y1   11T    6T     5T   54%\n/dev/grid/node-x0-y2   32T   28T     4T   87%\n/dev/grid/node-x1-y0    9T    7T     2T   77%\n/dev/grid/node-x1-y1    8T    0T     8T    0%\n/dev/grid/node-x1-y2   11T    7T     4T   63%\n/dev/grid/node-x2-y0   10T    6T     4T   60%\n/dev/grid/node-x2-y1    9T    8T     1T   88%\n/dev/grid/node-x2-y2    9T    6T     3T   66%")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day22-data.txt"))

(deftest part1-test
  (is (= 937 (part1 PUZZLE_INPUT))))

(deftest used-disk-test
  (is (true? (used-disk? {:x 1 :y 2 :size 5 :used 4 :avail 1})))
  (is (false? (used-disk? {:x 1 :y 2 :size 3 :used 0 :avail 3}))))

(deftest can-transfer-test
  (is (true? (can-transfer? {:x 1 :y 2 :size 5 :used 4 :avail 1}
                            {:x 2 :y 3 :size 10 :used 6 :avail 4})))
  (is (false? (can-transfer? {:x 1 :y 2 :size 5 :used 4 :avail 1}
                            {:x 2 :y 3 :size 10 :used 7 :avail 3}))))

(deftest eligible-pairs-test
  (let [p1 {:x 1 :y 2 :size 5 :used 4 :avail 1}
        p2 {:x 2 :y 3 :size 6 :used 0 :avail 6}
        p3 {:x 3 :y 4 :size 7 :used 4 :avail 3}]
    (is (= (list [p1 p2] [p3 p2])
           (eligible-pairs (list p1 p2 p3))))))

(deftest neighbor-test
  (is (true? (neighbor? {:x 1 :y 2} {:x 0 :y 2})))
  (is (true? (neighbor? {:x 1 :y 2} {:x 1 :y 1})))
  (is (false? (neighbor? {:x 1 :y 2} {:x 0 :y 1}))))

(deftest coords-of-test
  (is (= [2 3] (coords-of {:x 2 :y 3 :size 5 :used 4 :avail 1}))))

(deftest parse-line-test
  (is (= {:x 1 :y 2 :size 5 :used 4 :avail 1}
         (parse-line "/dev/grid/node-x1-y2   5T    4T     1T   80%"))))

(deftest part2-test
  (is (= 188 (incredibly-irritating-part-2 PUZZLE_INPUT))))
