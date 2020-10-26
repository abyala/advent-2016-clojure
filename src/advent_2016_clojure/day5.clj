(ns advent-2016-clojure.day5
  (:require [clojure.string :as str]
            [advent-2016-clojure.utils :as utils :only [md5]]))


(defn is-valid-hash?
  "Filters for valid hashes (strings that start with 5 zeroes)"
  [s] (str/starts-with? s "00000"))

(defn valid-hashes
  "Produces an infinite sequence of valid hashes, given the door-id prefix"
  [door-id]
  (->> (range)
       (map #(str door-id %))
       (map utils/md5)
       (filter is-valid-hash?)))

(defn build-password
  "Given a function with three parameters (map, position 5, position 6),
  applies the function to an infinite sequence of valid hashes, until the map
  has 8 values, at which point it returns the password."
  [reducing-fun door-id]
  (reduce #(let [m (reducing-fun %1 (get %2 5) (get %2 6))]
             (if (>= (count m) 8)
               (reduced (apply str (vals (sort m))))
               m))
          {}
          (valid-hashes door-id)))

(defn part1
  "Builds the password by mapping each pos5 character to its index in the map"
  [input]
  (build-password
    (fn [answer p5 _]
      (merge {(count answer) p5} answer))
    input))

(defn part2
  "Builds the password by mapping each pos6 character to the pos5 index, if not
  already mapped."
  [input]
  (build-password
    (fn [answer p5 p6]
      (let [p5-int (- (int p5) (int \0))]
        (if (<= p5-int 7) (merge {p5-int p6} answer) answer)))
    input))
