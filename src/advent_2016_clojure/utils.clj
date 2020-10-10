(ns advent-2016-clojure.utils)

(defn abs [v]
  (if (neg? v) (- 0 v) v))
