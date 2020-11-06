(ns advent-2016-clojure.day21
  (:require [clojure.string :as str]))

(defn swap-positions [pos-x pos-y letters]
  (let [x (letters pos-x) y (letters pos-y)]
    (-> letters
        (assoc pos-x y)
        (assoc pos-y x))))

(defn swap-letters [x y letters]
  (-> letters
      (assoc (.indexOf letters x) y)
      (assoc (.indexOf letters y) x)))

(defn rotate-count [dir x letters]
  (let [len (count letters)
        rot (mod x len)]
    (if (= dir "left")
      (vec (->> (cycle letters) (drop rot) (take len)))
      (vec (->> (cycle letters) (drop (- len rot)) (take len))))))

(defn rotate-by-letter [x letters]
  (let [index (.indexOf letters x)
        rot (+ (inc index)
               (if (>= index 4) 1 0))]
    (rotate-count "right" rot letters)))

(defn reverse-positions [x y letters]
  (vec (concat (subvec letters 0 x)
               (reverse (subvec letters x (inc y)))
               (subvec letters (inc y)))))

(defn remove-from-index [x letters]
  (vec (concat (subvec letters 0 x)
               (subvec letters (inc x)))))

(defn insert-at-index [x index letters]
  (vec (concat (subvec letters 0 index)
               [x]
               (subvec letters index))))

(defn move-by-position [x y letters]
  (let [c (get letters x)]
    (->> letters
         (remove-from-index x)
         (insert-at-index c y))))

(defn parse-line [line]
  (or (when-let [[_ x y] (re-matches #"swap position (\w) with position (\w)" line)]
        (partial swap-positions (Integer/parseInt x) (Integer/parseInt y)))
      (when-let [[_ x y] (re-matches #"swap letter (\w) with letter (\w)" line)]
        (partial swap-letters (first x) (first y)))
      (when-let [[_ dir x] (re-matches #"rotate (\w+) (\d) step.*" line)]
        (partial rotate-count dir (Integer/parseInt x)))
      (when-let [[_ x] (re-matches #"rotate based on position of letter (\w)" line)]
        (partial rotate-by-letter (first x)))
      (when-let [[_ x y] (re-matches #"reverse positions (\d) through (\d)" line)]
        (partial reverse-positions (Integer/parseInt x) (Integer/parseInt y)))
      (when-let [[_ x y] (re-matches #"move position (\d) to position (\d)" line)]
        (partial move-by-position (Integer/parseInt x) (Integer/parseInt y)))))

(defn apply-operation [op letters]
  ((parse-line op) letters))

(defn part1 [input initial-password]
  (apply str (reduce #(apply-operation %2 %1)
               (vec initial-password)
               (str/split-lines input))))