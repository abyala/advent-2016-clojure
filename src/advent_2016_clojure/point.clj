(ns advent-2016-clojure.point
  (:require [advent-2016-clojure.utils :refer [abs]]))

(defn add [[x1 y1] [x2 y2]] [(+ x1 x2) (+ y1 y2)])
(defn distance [[x1 y1] [x2 y2]]
  (+ (abs (- x1 x2))
     (abs (- y1 y2))))
