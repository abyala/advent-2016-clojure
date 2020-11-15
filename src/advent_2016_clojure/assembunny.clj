(ns advent-2016-clojure.assembunny
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
(defn toggle [x registers instructions index]
  (let [target-index (+ (val-or-register registers x) index)]
    (if (< target-index (count instructions))
      (let [instruction (instructions target-index)
            old-cmd (first instruction)
            new-cmd (if (= 2 (count instruction))
                      (if (= old-cmd "inc") "dec" "inc")
                      (if (= old-cmd "jnz") "cpy" "jnz"))]
        (assoc-in instructions [target-index 0] new-cmd))
      instructions)))

(defn apply-instruction
  "Returns a map {:registers registers, :jump-by jump-by}
   with instructions on the next registers and instruction jump amount."
  [instructions index registers]
  (let [[cmd x y] (get instructions index)]
    (merge {:registers registers :jump-by 1 :instructions instructions}
           (case cmd
             "cpy" {:registers (copy-reg x y registers)}
             "inc" {:registers (inc-reg x registers)}
             "dec" {:registers (dec-reg x registers)}
             "jnz" {:jump-by (or (jump-reg x y registers) 1)}
             "tgl" {:instructions (toggle x registers instructions index)}))))

(defn input-to-instructions [input]
  (->> (str/split-lines input)
       (map #(str/split % #" "))
       vec))

(defn solve [input starting-registers]
  (loop [instructions (input-to-instructions input)
         index 0
         registers starting-registers]
    (if (>= index (count instructions))
      (registers "a")
      (let [applied (apply-instruction instructions index registers)]
        (recur (applied :instructions)
               (+ index (applied :jump-by))
               (applied :registers))))))

(def empty-registers {"a" 0 "b" 0 "c" 0 "d" 0})
