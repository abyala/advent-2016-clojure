(ns advent-2016-clojure.day18
  (:require [advent-2016-clojure.utils :as utils]))

(defn parse-traps [line]
  (mapv #(= % \^) line))

(defn next-line [line]
  (mapv (fn [i] (utils/xor (get line (dec i))
                           (get line (inc i))))
        (range (count line))))

(defn trap-sequence [line]
  (lazy-seq (cons line (trap-sequence (next-line line)))))

(defn num-safe-tiles [input num-rows]
  (->> input
       parse-traps
       trap-sequence
       (take num-rows)
       (map #(count (filter false? %)))
       (apply +)))