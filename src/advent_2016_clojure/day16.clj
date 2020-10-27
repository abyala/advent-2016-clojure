(ns advent-2016-clojure.day16
  (:require [clojure.string :as str]))

(defn swap-zeros-and-ones [s]
  (->> s (map #(if (= % \1) \0 \1)) (apply str)))

(defn dragon-curve [data]
  (->> data
       str/reverse
       swap-zeros-and-ones
       (str data "0")))

(defn fill-disk [data len]
  (loop [s data]
    (if (>= (count s) len)
      (subs s 0 len)
      (recur (dragon-curve s)))))

(defn checksum [data]
  (loop [s data]
    (let [check (->> s
                     (partition 2)
                     (map (fn [[a b]] (if (= a b) \1 \0)))
                     (apply str))]
      (if (odd? (count check))
        check
        (recur check)))))

(defn solve [input len]
  (-> input
      (fill-disk len)
      checksum))

(defn part1 [input] (solve input 272))
(defn part2 [input] (solve input 35651584))