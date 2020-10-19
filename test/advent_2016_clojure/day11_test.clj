(ns advent-2016-clojure.day11-test
  (:require [clojure.test :refer :all]
            [advent-2016-clojure.day11 :refer :all]))

(def hydrogen-chip {:type :chip :element "hydrogen"})
(def lithium-chip {:type :chip :element "lithium"})
(def hydrogen-gen {:type :generator :element "hydrogen"})
(def lithium-gen {:type :generator :element "lithium"})
(def TEST_INPUT "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\nThe second floor contains a hydrogen generator.\nThe third floor contains a lithium generator.\nThe fourth floor contains nothing relevant.")
(def PUZZLE_INPUT (slurp "test\\advent_2016_clojure\\day11-data.txt"))

(deftest possible-next-floors-test
  (is (= '(2) (possible-next-floors 1)))
  (is (= '(1 3) (possible-next-floors 2)))
  (is (= '(2 4) (possible-next-floors 3)))
  (is (= '(3) (possible-next-floors 4))))

(deftest possible-pairs-test
  (is (= () (possible-pairs ())))
  (is (= () (possible-pairs (list hydrogen-chip))))
  (is (= (list [hydrogen-chip lithium-chip])
         (possible-pairs (list hydrogen-chip lithium-chip))))
  (is (= (list [hydrogen-chip lithium-chip] [hydrogen-chip hydrogen-gen] [lithium-chip hydrogen-gen])
         (possible-pairs (list hydrogen-chip lithium-chip hydrogen-gen)))))

(deftest possible-items-to-take-test
  (is (= '() (possible-items-to-take '())))
  (is (= (list [hydrogen-chip])
         (possible-items-to-take (list hydrogen-chip))))
  ; I don't care about the orders for the test cases, so set comparisons are fine
  (is (= (into #{} (list [hydrogen-chip] [lithium-chip] [hydrogen-chip lithium-chip]))
         (into #{} (possible-items-to-take (list hydrogen-chip lithium-chip))))))

(deftest current-floor-num-test
  (is (= 1 (current-floor-num {:floors {} :elevator 1}))))

(deftest current-floor-items-test
  (is (= (list hydrogen-chip lithium-chip)
         (current-floor-items {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)}})))
  (is (= ()
         (current-floor-items {:elevator 2 :floors {1 (list hydrogen-chip lithium-chip)}}))))

(deftest safe-floor-test
  (is (true? (safe-floor? ())))
  (is (true? (safe-floor? (list hydrogen-chip))))
  (is (true? (safe-floor? (list hydrogen-gen))))
  (is (true? (safe-floor? (list hydrogen-chip hydrogen-gen))))
  (is (true? (safe-floor? (list hydrogen-chip lithium-chip))))
  (is (false? (safe-floor? (list lithium-chip hydrogen-gen))))
  (is (false? (safe-floor? (list hydrogen-chip lithium-chip hydrogen-gen)))))

(deftest safe-state-test
  (is (true? (safe-state? {:floors {1 (list hydrogen-chip lithium-chip) 2 (list hydrogen-gen)}})))
  (is (true? (safe-state? {:floors {1 (list lithium-chip) 2 (list hydrogen-chip hydrogen-gen)}})))
  (is (false? (safe-state? {:floors {1 (list hydrogen-chip) 2 (list lithium-chip hydrogen-gen)}}))))

(deftest final-state-test
  (is (true? (final-state? {:elevator 4 :floors {1 () 2 () 3 ()
                                                     4 (list hydrogen-chip lithium-chip hydrogen-gen)}})))
  (is (false? (final-state? {:elevator 4 :floors {1 () 2 () 3 (list hydrogen-chip)
                                                     4 (list lithium-chip hydrogen-gen)}}))))

(deftest update-floor-test
  (is (= {:elevator 1 :floors {1 "abc" 2 "def"}}
         (update-floor "abc" 1 {:elevator 1 :floors {1 "xyz" 2 "def"}}))))

(deftest remove-from-floor-test
  (is (= {:elevator 1 :floors {1 ()}}
         (remove-from-floor (list hydrogen-chip lithium-chip) 1
                            {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)}})))
  (is (= {:elevator 1 :floors {1 (list hydrogen-gen)}}
         (remove-from-floor (list hydrogen-chip lithium-chip) 1
                            {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip hydrogen-gen)}}))))

(deftest add-to-floor-test
  (is (= {:elevator 1 :floors {1 (list hydrogen-chip)}}
         (add-to-floor (list hydrogen-chip) 1 {:elevator 1 :floors {1 ()}})))
  (is (= {:elevator 1 :floors {1 (list lithium-chip hydrogen-chip hydrogen-gen)}}
         (add-to-floor (list hydrogen-chip lithium-chip) 1 {:elevator 1 :floors {1 (list hydrogen-gen)}})))
  (is (= {:elevator 1 :floors {1 (list lithium-chip) 2 (list hydrogen-chip)}}
         (add-to-floor (list lithium-chip) 1 {:elevator 1 :floors {2 (list hydrogen-chip)}}))))

(deftest move-elevator-test
  (is (= {:elevator 2 :floors {1 (list hydrogen-chip)}}
         (move-elevator 2 {:elevator 1 :floors {1 (list hydrogen-chip)}}))))

(deftest move-items-test
  (is (= {:elevator 1 :floors {1 (list hydrogen-chip)}}
         (move-items {:elevator 1 :floors {1 ()}} 1 (list hydrogen-chip))))
  (is (= {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)}}
         (move-items {:elevator 1 :floors {1 (list lithium-chip)}} 1 (list hydrogen-chip))))
  (is (= {:elevator 1 :floors {1 (list hydrogen-chip)}}
         (move-items {:elevator 1} 1 (list hydrogen-chip))))
  (is (= {:elevator 2 :floors {1 (), 2 (list hydrogen-chip)}}
         (move-items {:elevator 1 :floors {1 (list hydrogen-chip)}} 2 (list hydrogen-chip)))))

(deftest possible-next-states-test
  (is (= (list {:elevator 2 :floors {1 (list lithium-chip) 2 (list hydrogen-chip hydrogen-gen)}})
         (possible-next-states {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip) 2 (list hydrogen-gen)}})))
  (is (= (list {:elevator 1 :floors {1 (list hydrogen-chip) 2 ()}}
               {:elevator 3 :floors {3 (list hydrogen-chip) 2 ()}})
         (possible-next-states {:elevator 2 :floors {2 (list hydrogen-chip)}}))))

(deftest state-summary-test
  (is (= {:elevator 1 :elements (list {:chip 1 :generator 2} {:chip 1 :generator 2})}
        (state-summary {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)
                                             2 (list hydrogen-gen lithium-gen)}})))
  (is (= {:elevator 1 :elements (list {:chip 1 :generator 2} {:chip 1 :generator 3})}
         (state-summary {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)
                                              2 (list hydrogen-gen)
                                              3 (list lithium-gen)}})))
  (is (= (state-summary {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)
                                              2 (list hydrogen-gen)
                                              3 (list lithium-gen)}})
         (state-summary {:elevator 1 :floors {1 (list hydrogen-chip lithium-chip)
                                              2 (list lithium-gen)
                                              3 (list hydrogen-gen)}}))))

(deftest parse-line-test
  (is (= () (parse-line "The first floor contains nothing relevant.")))
  (is (= (list hydrogen-chip) (parse-line "The first floor contains a hydrogen-compatible microchip.")))
  (is (= (list lithium-chip hydrogen-chip) (parse-line "The second floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.")))
  (is (= (list hydrogen-gen hydrogen-chip) (parse-line "The third floor contains a hydrogen-compatible microchip and a hydrogen generator."))))

(deftest part1-test
  (testing "Sample data"
    (is (= 11 (part1 TEST_INPUT))))
  (testing "Puzzle data"
    (is (= 33 (part1 PUZZLE_INPUT)))))
