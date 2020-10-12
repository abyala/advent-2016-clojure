(ns advent-2016-clojure.day7
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn is-abba? [word]
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

(defn ab-pairs [word]
  (set (keep #(let [[a b c] (subs word %)]
                (when (and (= a c) (not= a b)) [a b]))
             (range 0 (- (count word) 2)))))

(defn ba-pairs [word]
  (->> word
       ab-pairs
       (map (fn [[a b]] [b a]))))

(defn supports-ssl? [input]
  (let [{seqs :seqs hyper :hyper} (address-components input)]
    ((complement empty?) (set/intersection (reduce into #{} (map ab-pairs seqs))
                                           (reduce into #{} (map ba-pairs hyper))))))

(defn solve [f input] (->> input str/split-lines (filter f) count))
(defn part1 [input] (solve supports-tls? input))
(defn part2 [input] (solve supports-ssl? input))