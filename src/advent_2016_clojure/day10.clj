(ns advent-2016-clojure.day10
  (:require [clojure.string :as str]))

(defn initial-state "Initializes the program state before applying any lines" [lines]
  {:bots {} :outputs {} :algs {}})

(defn remove-chips "Takes away all chips from a bot by name" [name state]
  (update state :bots #(assoc % name '())))

(defn give-chip "Gives a chip to either a \"bot\" or \"output\" by name." [chip type-name name state]
  (update state
          (case type-name "bot" :bots "output" :outputs)
          (fn [col] (update col name #(cons chip %)))))

(defn move-chips "Removes the two chips from the sending bot to two other bots or outputs."
  [snd rcv1-type rcv1 rcv2-type rcv2 state]
  (let [[low high] (sort (take 2 ((state :bots) snd)))]
    (->> state
         (remove-chips snd)
         (give-chip low rcv1-type rcv1)
         (give-chip high rcv2-type rcv2))))

(defn bots-available-to-move "Returns the IDs of all bots who could possibly move chips." [state]
  (keep (fn [[k v]] (when (= 2 (count v)) k))
        (state :bots)))

(defn move-if-possible "If a bot is able to move chips, returns {:comparison [bot (chips)], :state new-state]}"
  [state]
  (when-let [bot (first (bots-available-to-move state))]
    (let [comparison [bot ((state :bots) bot)]]
      {:comparison comparison :state (((state :algs) bot) state)})))

(defn initialize-state-with-line "Reads a line of input and applies it to the state."
  [state line]
  (if-let [[_ & matches] (re-matches #"value (\d+) goes to bot (\d+)" line)]
    (let [[value bot] (map #(Integer/parseInt %) matches)]
      (give-chip value "bot" bot state))
    (let [[_ snd-str rcv1-type rcv1-str rcv2-type rcv2-str] (re-matches #"bot (\d+) gives low to (\w+) (\d+) and high to (\w+) (\d+)" line)
          [snd rcv1 rcv2] (map #(Integer/parseInt %) (list snd-str rcv1-str rcv2-str))]
      (update state :algs #(assoc % snd (partial move-chips snd rcv1-type rcv1 rcv2-type rcv2))))))

(defn initialize-input "Creates a new state and applies it to all lines in the input."
  [input]
  (let [lines (str/split-lines input)]
    (reduce initialize-state-with-line (initial-state lines) lines)))

(defn part1 [input chip1 chip2]
  (loop [state (initialize-input input)]
    (let [{[bot chips] :comparison s :state} (move-if-possible state)]
      (if (= (sort (list chip1 chip2))
             (sort chips))
        bot
        (recur s)))))

(defn part2 [input]
  (loop [state (initialize-input input)]
    (if (every? #(contains? (state :outputs) %) '(0 1 2))
      (apply * (map #(first ((state :outputs) %)) '(0 1 2)))
      (recur ((move-if-possible state) :state)))))