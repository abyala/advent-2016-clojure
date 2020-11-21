(ns advent-2016-clojure.day25
  (:require [advent-2016-clojure.assembunny :as bunny]
            [clojure.core.async :refer [poll! take! <!!]]))

(defn channel-to-sequence [c]
  (when-let [v (<!! c)]
    (lazy-seq (cons v (channel-to-sequence c)))))

(defn equals-seqs? [s1 s2 test-count]
  (every? #(apply = %)
          (take test-count (partition 2 (interleave s1 s2)))))

(def clock-signal (cycle '(0 1)))
(defn clock-signal? [signal]
  (equals-seqs? signal clock-signal 1000))

(defn part1 [input]
  (loop [n 0]
    (let [c (bunny/compute-output-transmissions input (assoc bunny/empty-registers "a" n))]
      (let [signal? (clock-signal? (channel-to-sequence c))]
        (if signal? n (recur (inc n)))))))

