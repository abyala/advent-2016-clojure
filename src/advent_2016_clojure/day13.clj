(ns advent-2016-clojure.day13
  (:require [advent-2016-clojure.point :as point])
  (:require [clojure.string :as str]))

(defn to-binary-str [n]
  (-> n str BigInteger. (.toString 2)))

(defn count-binary-ones [n]
  (->> n to-binary-str (filter #(= % \1)) count))

(defn within-map? [[x y]] (and (>= x 0) (>= y 0)))

(defn open-space? [fav [x y]]
  (let [calc (+ (* x x)
                (* 3 x)
                (* 2 x y)
                y
                (* y y))]
    (even? (count-binary-ones (+ calc fav)))))

(defn walkable? [fav point]
  (and (within-map? point) (open-space? fav point)))

(defn walkable-neighbors [fav point]
  (let [options '([0 1] [0 -1] [1 0] [-1 0])]
    (->> options
         (map #(point/add point %))
         (filter #(walkable? fav %)))))

(defn a-star-estimate [distance-already from to]
  (+ distance-already (point/distance from to)))

(defn solve [fav target]
  ; "options" are defined as [distance-to [x y]]
  (let [option-comparator (fn [[d [x y]]] [(a-star-estimate d [x y] target) x y])
        initial-option [0 [1 1]]]
    (loop [options (sorted-set-by #(compare (option-comparator %1)
                                            (option-comparator %2))
                                  initial-option)
           seen #{}]
      (let [next-point (first options), [dist point] next-point]
        (if (= point target)
          dist
          (let [next-options (->> point
                                  (walkable-neighbors fav)
                                  (filter #(not (contains? seen %)))
                                  (map #(vector (inc dist) % )))]
            (recur (-> options
                       (disj next-point)
                       ((partial apply conj) next-options))
                   (conj seen point))))))))

(defn part1 [favorite-number]
  (solve favorite-number [31 39]))