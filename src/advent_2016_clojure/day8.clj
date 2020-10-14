(ns advent-2016-clojure.day8
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn blank-screen [width height] {:width width :height height :pixels #{}})

(defn update-pixels [f screen]
  (assoc screen :pixels (f screen)))

(defn rect [a b screen]
  (apply conj (screen :pixels) (for [x (range a)
                                     y (range b)]
                                 [x y])))

(defn rotate-col [col by screen]
  (let [to-rotate (filter (fn [[x _]] (= x col)) (screen :pixels))
        rotated (set (map (fn [[x y]] [x (-> y (+ by) (mod (screen :height)))]) to-rotate))]
    (-> (screen :pixels)
        (set/difference to-rotate)
        (set/union rotated))))

(defn rotate-row [row by screen]
  (let [to-rotate (filter (fn [[_ y]] (= y row)) (screen :pixels))
        rotated (set (map (fn [[x y]] [(-> x (+ by) (mod (screen :width) )) y]) to-rotate))]
    (-> (screen :pixels)
        (set/difference to-rotate)
        (set/union rotated))))

(defn parse-instruction [input]
  (or (when-let [[_ a b] (re-matches #"rect (\d+)x(\d+)" input)]
        (partial rect (Integer/parseInt a) (Integer/parseInt b)))
      (when-let [[_ col by] (re-matches #"rotate column x=(\d+) by (\d+)" input)]
        (partial rotate-col (Integer/parseInt col) (Integer/parseInt by)))
      (when-let [[_ row by] (re-matches #"rotate row y=(\d+) by (\d+)" input)]
        (partial rotate-row (Integer/parseInt row) (Integer/parseInt by)))))

(defn run-program [width height inputs]
  (reduce #(update-pixels %2 %1)
          (blank-screen width height)
          (map parse-instruction inputs)))

(defn part1 [input]
  (->> input
       str/split-lines
       (run-program 50 6)
       :pixels
       count))