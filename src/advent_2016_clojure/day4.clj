(ns advent-2016-clojure.day4
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[_ name id checksum] (re-matches #"(.*)-(\d+)\[(\w{5})\]" line)]
    {:name name :id (Integer/parseInt id) :checksum checksum}))

(defn room-checksum [name]
  (->> name
       (filter #(Character/isLetter %))
       frequencies
       (sort-by (juxt #(- (second %)) first))
       keys
       (take 5)
       (apply str)))

(defn checksum-matches? [room]
  (= (:checksum room) (room-checksum (:name room))))

(defn part1 [input]
  (->> input
       str/split-lines
       (map parse-line)
       (filter checksum-matches?)
       (map :id)
       (reduce +)))