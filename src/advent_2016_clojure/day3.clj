(ns advent-2016-clojure.day3
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (map #(Integer/parseInt %)
       (-> line str/trim (str/split #" +"))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn is-triangle? [sides]
  (let [[small2 largest] (map #(reduce + %)
                              (partition-all 2 (sort sides)))]
    (> small2 largest)))

(defn part1 [input]
  (->> input
       parse-input
       (filter is-triangle?)
       count))