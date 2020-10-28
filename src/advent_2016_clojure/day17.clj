(ns advent-2016-clojure.day17
  (:require [advent-2016-clojure.utils :as utils :only [md5]]
            [advent-2016-clojure.point :as point]))

(def target [3 0])
(defn door-open? [c] (>= (compare c \b) 0))

(defn open-door-hashes [passcode path]
  (->> path
       (str passcode)
       utils/md5
       (zipmap [:up :down :left :right])
       (keep (fn [[dir c]] (when (door-open? c) dir)))))

(defn move-dir [[x y] dir]
  (case dir
    :up [x (inc y)]
    :down [x (dec y)]
    :left [(dec x) y]
    :right [(inc x) y]))

(defn on-map? [[x y]]
  (and (<= 0 x 3) (<= 0 y 3)))

(defn append-to-path [path dir]
  (str path (case dir :up \U :down \D :left \L :right \R)))

(defn next-moves [passcode path point]
  (->> path
       (open-door-hashes passcode)
       (map #(hash-map :dir % :point (move-dir point %) :path (append-to-path path %)))
       (filter #(on-map? (:point %)))))

(defn paths-to-vault [passcode]
  (loop [options '({:path "" :point [0 3]})
         found ()]
    (let [[{path :path point :point} & others] options]
      (if (nil? point)
        found
        (if (= point target)
          (recur others (cons path found))
          (recur (into others (next-moves passcode path point))
                 found))))))

(defn part1 [passcode]
  (->> (paths-to-vault passcode)
       (sort-by count)
       first))

(defn part2 [passcode]
  (->> (paths-to-vault passcode)
       (sort-by count)
       last
       count))