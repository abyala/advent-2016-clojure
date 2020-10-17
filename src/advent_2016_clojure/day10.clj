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
  {:bots (-> lines max-bot init-bots) :algs {}})

(defn give-chip-to-bot [chip bot bots]
  (update bots bot #(cons chip %)))

(defn move-chips [from to-a to-b bots]
  (let [[low high] (sort (take 2 (bots from)))]
    (-> bots
        (assoc from '())
        (update to-a #(cons low %))
        (update to-b #(cons high %)))))

(defn process-line [state line]
  (if-let [[_ & matches] (re-matches #"value (\d+) goes to bot (\d+)" line)]
    (let [[value bot] (map #(Integer/parseInt %) matches)]
      (update state :bots #(give-chip-to-bot value bot %)))
    (let [[_ & matches] (re-matches #"bot (\d+) gives low to bot (\d+) and high to bot (\d+)" line)
          [bot-a bot-b bot-c] (map #(Integer/parseInt %) matches)]
      (println "I want to associate an algorithm to " bot-a)
      (println "\tLine: " line)
      (println "\tMatches: " matches)
      (update state :algs #(assoc % bot-a (partial move-chips bot-a bot-b bot-c))))))

