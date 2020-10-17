(ns advent-2016-clojure.day10
  (:require [clojure.string :as str]))

(defn max-bot [lines]
  (->> lines
       (mapcat (fn [s] (re-seq #"bot (\d+)" s)))
       (map second)
       (map #(Integer/parseInt %))
       (apply max)))

(defn init-bots [max-bot]
  (->> max-bot inc range
       (map #(vector % '()))
       (into {})))

(defn initial-state [lines]
  {:bots (-> lines max-bot init-bots) :outputs {} :algs {}})

(defn remove-chips [name state]
  (update state :bots #(assoc % name '())))

(defn give-chip [chip type-name name state]
  (update state
          (case type-name "bot" :bots "output" :outputs)
          (fn [col] (update col name #(cons chip %)))))

(defn move-chips [snd rcv1-type rcv1 rcv2-type rcv2 state]
  (let [[low high] (sort (take 2 ((state :bots) snd)))]
    (->> state
         (remove-chips snd)
         (give-chip low rcv1-type rcv1)
         (give-chip high rcv2-type rcv2))))

(defn bots-available-to-move [state]
  (keep (fn [[k v]] (when (= 2 (count v)) k))
        (state :bots)))

(defn move-if-possible [state]
  (when-let [bot (first (bots-available-to-move state))]
    (let [comparison [bot ((state :bots) bot)]]
      {:comparison comparison :state (((state :algs) bot) state)})))

(defn process-line [state line]
  (if-let [[_ & matches] (re-matches #"value (\d+) goes to bot (\d+)" line)]
    (let [[value bot] (map #(Integer/parseInt %) matches)]
      (give-chip value "bot" bot state))
    (let [[_ snd-str rcv1-type rcv1-str rcv2-type rcv2-str] (re-matches #"bot (\d+) gives low to (\w+) (\d+) and high to (\w+) (\d+)" line)
          [snd rcv1 rcv2] (map #(Integer/parseInt %) (list snd-str rcv1-str rcv2-str))]
      (update state :algs #(assoc % snd (partial move-chips snd rcv1-type rcv1 rcv2-type rcv2))))))

(defn process-input [input]
  (let [lines (str/split-lines input)]
    (reduce process-line (initial-state lines) lines)))

(defn part1 [input chip1 chip2]
  (loop [state (process-input input)]
    (let [{[bot chips] :comparison s :state} (move-if-possible state)]
      (if (= (sort (list chip1 chip2))
             (sort chips))
        bot
        (recur s)))))
