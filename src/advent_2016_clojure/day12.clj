(ns advent-2016-clojure.day12
  (:require [clojure.string :as str]))

(defn maybe-parse-int [v]
  (try (Integer/parseInt v)
       (catch NumberFormatException _ nil)))

(defn val-or-register [registers v]
  (or (maybe-parse-int v)
      (registers v)))

(defn copy-reg [x y registers]
  (->> x
       (val-or-register registers)
       (assoc registers y)))

(defn inc-reg [x registers] (update registers x inc))
(defn dec-reg [x registers] (update registers x dec))
(defn jump-reg [x y registers]
  (when (not= 0 (val-or-register registers x))
    (val-or-register registers y)))

(defn apply-instruction [instruction registers]
  (let [[cmd x y] (str/split instruction #" ")]
    (merge {:registers registers :jump-by 1}
           (case cmd
             "cpy" {:registers (copy-reg x y registers)}
             "inc" {:registers (inc-reg x registers)}
             "dec" {:registers (dec-reg x registers)}
             "jnz" {:jump-by (or (jump-reg x y registers) 1)}))))

(defn solve [input starting-registers]
  (let [lines (vec (str/split-lines input))]
    (loop [registers starting-registers index 0]
      (if (>= index (count lines))
        (registers "a")
        (let [applied (apply-instruction (get lines index) registers)]
          (recur (applied :registers)
                 (+ index (applied :jump-by))))))))

(def empty-registers {"a" 0 "b" 0 "c" 0 "d" 0})
(defn part1 [input]
  (solve input empty-registers))

(defn part2 [input]
  (solve input (assoc empty-registers "c" 1)))