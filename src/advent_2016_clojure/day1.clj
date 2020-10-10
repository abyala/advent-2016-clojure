(ns advent-2016-clojure.day1
  (:require [clojure.string :as str]
            [advent-2016-clojure.utils :refer [abs] ]))

(defn parse-move [input]
  [(first input) (Integer/parseInt (subs input 1))])

(defn parse-input [input]
  (map parse-move (str/split input #", ")))

(def directions [:north :east :south :west])

; STATE: dir, loc
(defn turn [state face]
  (let [fun (case face \R inc \L dec)
        idx (mod (fun (.indexOf directions (:dir state)))
                 (count directions))
        new-dir (directions idx)]
    (assoc state :dir new-dir)))

(defn step [state steps]
  (let [loc (:loc state)]
    (assoc state :loc (case (:dir state)
                        :north (update loc 1 #(+ % steps))
                        :south (update loc 1 #(- % steps))
                        :east (update loc 0 #(+ % steps))
                        :west (update loc 0 #(- % steps))))))

(defn street-distance [loc]
  (reduce + (map abs loc)))

(defn follow-steps [steps]
  (reduce (fn [state [face dist]]
            (-> state
                (turn face)
                (step dist)))
          {:dir :north, :loc [0 0]}
          steps))

(defn part1 [input]
  (->> input
       parse-input
       follow-steps
       :loc
       street-distance))