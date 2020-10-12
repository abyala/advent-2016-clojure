(ns advent-2016-clojure.day7
  (:require [clojure.string :as str]))


(defn is-abba? [word ]
  (loop [s word]
    (when (>= (count s) 4)
      (let [[a b c d] (take 4 s)]
        (or (and (= a d) (= b c) (not= a b))
            (recur (subs s 1)))))))

(defn address-components [input]
  (loop [seqs (), hyper (), s input]
    (let [idx (str/index-of s \[)]
      (cons s seqs)
      (if (nil? idx)
        {:seqs (cons s seqs) :hyper hyper}
        (let [close-idx (str/index-of s \])]
          (recur (cons (subs s 0 idx) seqs)
                 (cons (subs s (inc idx) close-idx) hyper)
                 (subs s (inc close-idx))))))))

(defn supports-tls? [input]
  (let [{seqs :seqs hyper :hyper} (address-components input)]
    (and (some is-abba? seqs)
         (not-any? is-abba? hyper))))

(defn part1 [input]
  (->> input
      str/split-lines
      (filter supports-tls?)
      count))
