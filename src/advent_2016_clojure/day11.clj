(ns advent-2016-clojure.day11
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;state: {:elevator 1, :floors {1 ({:type :chip :element "hydrogen"}, {:type :chip :element "lithium"}),
;                              2 ({:type :generator :element "hydrogen"})
;                              3 ({:type :generator :element "lithium"})}}

(defn possible-next-floors [floor]
  (filter #(and (> % 0) (< % 5))
          (list (dec floor) (inc floor))))

(defn possible-pairs [line]
  (filter (fn [[i j]] (neg? (compare [(:type i) (:element i)]
                                     [(:type j) (:element j)])))
          (for [i line j line] [i j])))

(defn possible-items-to-take [line]
  (apply merge (possible-pairs line)
         (map vector line)))

(defn current-floor-num [state] (-> state :elevator))
(defn current-floor-items [state]
  (-> state :floors (get (current-floor-num state)) (or '())))

(defn safe-floor? [items]
  (let [{chips :chip gens :generator} (group-by :type items)]
    (or (nil? chips)
        (nil? gens)
        (empty? (set/difference (into #{} (map :element chips))
                                (into #{} (map :element gens)))))))

(defn safe-state? [state]
  (->> state
       :floors
       vals
       (map safe-floor?)
       (every? true?)))

(defn final-state? [state]
  (->> (dissoc (state :floors) 4)
       vals
       (every? empty?)))

(defn update-floor [new-floor num state]
  (update state :floors #(assoc % num new-floor)))

(defn remove-from-floor [items num state]
  (let [floor (as-> state x
                    (x :floors)
                    (get x num)
                    (remove #(contains? (set items) %) x))]
    (update-floor floor num state)))

(defn add-to-floor [items num state]
  (let [floor (as-> state x
                    (x :floors)
                    (get x num)
                    (or x ())
                    (apply merge x items))]
    (update-floor floor num state)))

(defn move-elevator [level state]
  (assoc state :elevator level))

(defn move-items [state next-floor moving]
  (let [current-floor (current-floor-num state)
        next-state (->> state
                        (remove-from-floor moving current-floor)
                        (add-to-floor moving next-floor)
                        (move-elevator next-floor))]
    (when (safe-state? next-state) next-state)))

(defn possible-next-states [state]
  (keep identity
        (for [floor (-> state current-floor-num possible-next-floors)
              moves (-> state current-floor-items possible-items-to-take)]
          (move-items state floor moves))))

(defn closest-candidate [candidates]
  (first (sort #(compare (:distance %1) (:distance %2)) candidates)))

(defn steps-to-move-up [initial-state]
  (loop [candidates #{{:state initial-state :distance 0}},
         seen #{}]
    ; Assume this puzzle has a completion, so no need for nil check
    (let [chosen-candidate (closest-candidate candidates)
          {next-state :state distance :distance} chosen-candidate]
      (if (final-state? next-state)
        distance
        (let [possible-candidates (possible-next-states next-state)
              unseen-candidates (apply disj (set possible-candidates) seen)]
          (recur (-> candidates
                     (disj chosen-candidate)
                     (set/union (map (fn [uc] {:state uc :distance (inc distance)}) unseen-candidates))
                     set)
                 (conj seen next-state)))))))

(defn element-of-type [t e] {:type t :element e})
(defn parse-line [line]
  (let [line (first (str/split-lines line))
        chips (when-let [matches (re-seq #"(\w+)-compatible microchip" line)]
                (map #(element-of-type :chip (second %)) matches))
        gens (when-let [matches (re-seq #"(\w+) generator" line)]
               (map #(element-of-type :generator (second %)) matches))]
    (reduce (partial apply merge) () (list chips gens))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)
       (map-indexed #(vector (inc %1) %2))
       (into {} )))

(defn initialize-state [input]
  {:elevator 1 :floors (parse-input input)})

(defn part1 [input]
  (-> input initialize-state steps-to-move-up))