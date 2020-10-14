(ns advent-2016-clojure.day9
  (:require [clojure.string :as str]))

(defn decompress [input]
  (loop [output "", s input]
    (if-let [[_ prefix num-chars num-repeats suffix]
             (re-matches #"([^\(]*)\((\d+)x(\d+)\)(.*)" s)]
      (let [[repeating leftovers] (map #(apply str %)
                                       (split-at (Integer/parseInt num-chars) suffix))]
        (recur (apply str output prefix (repeat (Integer/parseInt num-repeats) repeating))
               leftovers))
      (apply str (concat output s)))))

(defn part1 [input] (-> input decompress count))
