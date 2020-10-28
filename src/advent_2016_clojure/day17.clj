(ns advent-2016-clojure.day17
  (:require [clojure.string :as str]
            [advent-2016-clojure.utils :as utils :only [md5]]
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
  (and (>= x 0) (<= x 3)
       (>= y 0) (<= y 3)))

(defn append-to-path [path dir]
  (str path (case dir :up \U :down \D :left \L :right \R)))

(defn next-moves [passcode path point]
  (->> path
       (open-door-hashes passcode)
       (map #(hash-map :dir % :point (move-dir point %) :path (append-to-path path %)))
       (filter #(on-map? (:point %)))))

(defn a-star-estimate [{path :path point :point}]
  (+ (count path)
     (point/distance point target)))

(defn part1 [passcode]
  (loop [options #{{:path "" :point [0 3]}}]
    (let [option (first (sort-by #(a-star-estimate %) options))
          {path :path point :point} option]
      (if (= point target)
        path
        (recur (-> options
                   (disj option)
                   ((partial apply merge) (next-moves passcode path point))))))))