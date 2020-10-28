(ns advent-2016-clojure.day08
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn blank-screen [width height] {:width width :height height :pixels #{}})

(defn update-pixels [f screen]
  (assoc screen :pixels (f screen)))

(defn rect [a b screen]
  (apply conj (screen :pixels)
         (for [x (range a)
               y (range b)]
           [x y])))

(defn rotate [old-points new-points screen]
  (-> (screen :pixels)
      (set/difference old-points)
      (set/union new-points)))

(defn add-mod [x add-by mod-by] (-> x (+ add-by) (mod mod-by)))
(defn rotate-col [col by screen]
  (let [old-points (filter (fn [[x _]] (= x col))
                           (screen :pixels))
        new-points (set (map (fn [[x y]] [x (add-mod y by (screen :height))])
                             old-points))]
    (rotate old-points new-points screen)))

(defn rotate-row [row by screen]
  (let [old-points (filter (fn [[_ y]] (= y row)) (screen :pixels))
        new-points (set (map (fn [[x y]] [(add-mod x by (screen :width)) y])
                             old-points))]
    (rotate old-points new-points screen)))

(defn parse-instruction [input]
  (let [parsers (list [#"rect (\d+)x(\d+)" rect]
                      [#"rotate column x=(\d+) by (\d+)" rotate-col]
                      [#"rotate row y=(\d+) by (\d+)" rotate-row])]
    (first (keep (fn [[pat f]] (when-let [[_ a b] (re-matches pat input)]
                                 (partial f (Integer/parseInt a) (Integer/parseInt b))))
                 parsers))))

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

(defn print-row [row]
  (->> row sort (map second) (apply str)))

(defn display-screen [screen]
  (let [all-blanks (apply merge (for [x (range (:width screen))
                                      y (range (:height screen))]
                                  {[x y] \space}))
        actual-data (apply merge (map (fn [x] {x \#}) (:pixels screen)))]
    (->> actual-data
         (merge all-blanks)
         (group-by (comp second first))
         (sort)
         (map second)
         (map print-row)
         (run! println))))

(defn part2 [input]
  (->> input
       str/split-lines
       (run-program 50 6)
       display-screen))