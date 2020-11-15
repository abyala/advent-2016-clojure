(ns advent-2016-clojure.day24
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [advent-2016-clojure.point :as point]
            [advent-2016-clojure.utils :as utils]))

(def starting-pos \0)
(defn walkable? [c] (not= c \#))
(defn location? [c] (not= c \.))

(defn parse-line
  "Parses a line of text into a map of coordinates to non-wall characters"
  [line y]
  (->> line
       (keep-indexed (fn [x c] (when (walkable? c) {[x y] c})))
       (into {})))

(defn parse-maze [input]
  "Parses an input String into a maze, of format
   {:points #([x y]) :locations {c [x y]}"
  (let [lines (str/split-lines input)
        points (->> lines
                    (map-indexed (fn [y line] (parse-line line y)))
                    (apply merge))
        locations (->> points
                       (keep (fn [[coords v]] (when (location? v) [v coords])))
                       (into {}))]
    {:points (set (keys points)) :locations locations}))

(defn shortest-distance
  "Given a parsed maze, returns the fewest number of steps from point a to b"
  [{locations :locations points :points} loc-a loc-b]
  (let [source (locations loc-a)
        dest (locations loc-b)]
    (loop [possible #{{:point    source
                       :cost     0
                       :estimate (point/distance source dest)}}
           seen #{}]
      (let [choice (first (sort-by (juxt :estimate) possible))
            {point :point cost :cost} choice]
        (if (= point dest)
          cost
          (let [next-points (-> (point/adjacencies point)
                                set
                                (set/intersection points)
                                (set/difference seen))
                next-poss (->> next-points
                               (map #(hash-map :point %
                                               :cost (inc cost)
                                               :estimate (+ (inc cost)
                                                            (point/distance % dest))))
                               set)]
            (recur (-> (set/difference possible #{choice})
                       (set/union next-poss))
                   (conj seen point))))))))

(defn location-ids-in
  "Returns the list of all locations within the maze, as a sequence of chars."
  [maze]
  (map first (:locations maze)))

(defn all-direct-paths
  "Given a maze, returns a map of all pairs of location names and their distances.
   Format: {[id-1 id2] distance}"
  [maze]
  (->> (let [location-ids (location-ids-in maze)]
         (for [a location-ids
               b (filter #(< (int a) (int %)) location-ids)]
           (let [dist (shortest-distance maze a b)]
             {[a b] dist
              [b a] dist})))
       (into {})))

(defn sum-of-steps
  "Given a map of location pairs and their distances, plus a sequence of distances to follow,
  returns the total number of steps to go from location to location."
  [direct-paths step-order]
  (->> (reduce (fn [{last :last steps :steps} dest]
                 {:last dest, :steps (+ steps (direct-paths [last dest]))})
               {:last (first step-order), :steps 0}
               (rest step-order))
       :steps))

(defn solve2
  "Given an input string and a function to apply, determine the shortest number of steps from point \0
  through all locations, applying the input function path-fun to the list of intended steps."
  [input path-fun]
  (let [maze (parse-maze input)
        distances (all-direct-paths maze)]
    (->> (location-ids-in maze)
         utils/permutations
         (filter #(= starting-pos (first %)))
         (map path-fun)
         (map #(sum-of-steps distances %))
         (apply min))))

(defn part1 [input]
  (solve2 input identity))

(defn part2 [input]
  (solve2 input #(concat % (list starting-pos))))