; State: {
;   :disks {point->disk}
;   :target point
;   :cost
;   :estimate

(ns advent-2016-clojure.day22
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [advent-2016-clojure.point :as point]))

(defn used-disk? [{used :used}] (pos? used))
(defn can-transfer? [{src :used} {dest :avail}] (>= dest src))

(defn eligible-pairs [disks]
  (for [a (filter used-disk? disks)
        b (filter #(and (not= a %)
                        (can-transfer? a %))
                  disks)]
    [a b]))

(defn neighbor? [{x1 :x y1 :y} {x2 :x y2 :y}]
  (= 1 (point/distance [x1 y1] [x2 y2])))

(defn coords-of [{x :x y :y}] [x y])

(defn eligible-moves [disks]
  (->> (eligible-pairs disks)
       (keep (fn [[a b]] (when (neighbor? a b) [(coords-of a) (coords-of b)])))))

(defn parse-line [line]
  (let [tokens (re-matches #"/dev/grid/node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T\s+(\d+)T\s+(\d+)\%" line)
        [x y size used avail _] (->> (drop 1 tokens)
                                     (map #(Integer/parseInt %)))]
    {:x x :y y :size size :used used :avail avail}))

(defn parse-input [input]
  (->> (str/split-lines input)
       (drop 2)
       (map parse-line)))

(defn part1 [input]
  (->> (parse-input input)
       eligible-pairs
       count))
