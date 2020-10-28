(ns advent-2016-clojure.day04
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

(defn valid-rooms [input]
  (->> input
       str/split-lines
       (map parse-line)
       (filter checksum-matches?)))

(defn part1 [input]
  (->> input valid-rooms (map :id) (reduce +)))

(defn shift-cipher [c id]
  (-> c int (+ id -97) (mod 26) (+ 97) char))

(defn decrypt-name [name id]
  (apply str (map #(shift-cipher % id) name)))

(defn part2 [input]
  (->> input
       valid-rooms
       (map #(assoc % :decrypted-name (decrypt-name (:name %) (:id %))))
       (filter #(str/includes? (:decrypted-name %) "northpole"))
       (map :id)
       first))
