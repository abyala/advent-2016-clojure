(ns advent-2016-clojure.day14
  (:require [advent-2016-clojure.utils :as utils :only [md5]]
            [clojure.set :as set]))

(defn stretched-hash [s n]
  (reduce (fn [acc _] (utils/md5 acc)) s (range n)))

(defn salted-hashes
  ([salt hashings] (salted-hashes salt hashings 0))
  ([salt hashings n] (lazy-seq (cons (stretched-hash (str salt n) hashings)
                                     (salted-hashes salt hashings (inc n))))))

(defn repeats-of-length [length word]
  (->> (range (- (count word) (dec length)))
       (map #(subs word % (+ % length)))
       (filter #(apply = %))
       (map first)))
(defn first-triplet [word] (first (repeats-of-length 3 word)))
(defn all-quints [word] (set (repeats-of-length 5 word)))

(defn repeat-tuple [idx hash]
  [idx (first-triplet hash) (all-quints hash)])

(defn key? [triple others]
  (->> (map #(nth % 2) others)
       (some #(contains? % triple))))

(defn seq-of-hashes [window offset]
  ; Window is of form [ [idx triples fifths] ]
  (if (key? ((first window) 1)
            (->> window (take 1001) rest))
    (lazy-seq (cons offset (seq-of-hashes (rest window) (inc offset))))
    (lazy-seq (seq-of-hashes (rest window) (inc offset)))))

(defn windowed-hashes [salt hashings]
  (let [s (salted-hashes salt hashings)
        window (map-indexed #(repeat-tuple %1 %2) s)]
    (seq-of-hashes window 0)))

(defn nth-hash [n salt hashings]
  (last (take n (windowed-hashes salt hashings))))

(defn part1 [salt]
  (nth-hash 64 salt 1))

(defn part2 [salt]
  (nth-hash 64 salt 2017))