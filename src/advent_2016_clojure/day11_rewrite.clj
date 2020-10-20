(ns advent-2016-clojure.day11_rewrite
  (:require [clojure.string :as str]
            [clojure.set :as set]))

; Format of a state:
; {:elevator 1, :elements ({:chip 1 :generator 1}, {:chip 2 :generator 3})}

(defn parse-line [floor line]
  (let [chips (when-let [matches (re-seq #"(\w+)-compatible microchip" line)]
                (map #(vector {:element (second %)} {:chip floor}) matches))
        gens (when-let [matches (re-seq #"(\w+) generator" line)]
               (map #(vector {:element (second %)} {:generator floor}) matches))]
    (concat chips gens)))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map-indexed (fn [idx line] (parse-line (inc idx) line)))
       (apply concat)
       (group-by #((first %) :element))
       (map second)
       (map (fn [components] (into {} (map second components))))
       vec))

(defn initial-state [input]
  {:elevator 1 :elements (parse-input input)})

(defn floors-with-generators [state]
  (->> state :elements (map :generator) set))

(defn detached-element? [element]
  (not= (:chip element) (:generator element)))

(defn floors-with-detached-chips [state]
  (->> state :elements (filter detached-element?) (map :chip) set))

(defn safe-state? [state]
  (empty? (set/intersection (floors-with-detached-chips state)
                            (floors-with-generators state))))

(defn possible-next-floors [floor]
  (filter #(and (> % 0) (< % 5))
          (list (dec floor) (inc floor))))

(defn elements-on-floor [floor elements]
  (let [f (fn [t e] (keep-indexed #(when (= floor (t %2)) {:idx %1 :type t}) e))]
    (apply merge (f :chip elements) (f :generator elements))))

(defn move-options [floor elements]
  (let [floor-options (elements-on-floor floor elements)
        indexed (map-indexed #(vector %1 %2) floor-options)
        pairs (for [x indexed
                    y (filter #(> (first %) (first x)) indexed)]
                (list (second x) (second y)))]
    (concat (map list floor-options) pairs)))

(defn move-item-to-floor [item floor state]
  (assoc-in state [:elements (:idx item) (:type item)] floor))

(defn item-comparator [v1 v2]
  (compare [(:chip v1) (:generator v1)] [(:chip v2) (:generator v2)]))

(defn move-items [items floor state]
  (as-> state s
        (reduce #(move-item-to-floor %2 floor %1) s items)
        (assoc s :elevator floor)
        (update s :elements #(vec (sort item-comparator %)))))

(defn next-possible-states [state]
  (let [{floor :elevator elements :elements} state
        valid-states (for [f (possible-next-floors floor)
                           items (move-options floor elements)]
                       (move-items items f state))]
    (->> valid-states
         (filter safe-state?)
         set)))

(defn final-state? [state]
  (every? #(= % {:chip 4 :generator 4}) (state :elements)))

(defn solve [state]
  (loop [candidates #{{:state state :distance 0}}
         known #{state}]
    (let [chosen-candidate (first (sort-by :distance candidates))
          {next-state :state distance :distance} chosen-candidate]
      (if (final-state? next-state)
        distance
        (let [next-possible (next-possible-states next-state)
              unseen (keep #(when (not (contains? known %)) %) next-possible)
              new-candidates (set (map (fn [c] {:state c :distance (inc distance)}) unseen))]
          (recur (apply merge (disj candidates chosen-candidate) new-candidates)
                 (set/union known (set unseen))))))))

(defn part1 [input]
  (solve (initial-state input)))

(defn part2 [input]
  (-> input
      initial-state
      (update :elements #(conj % {:chip 1 :generator 1} {:chip 1 :generator 1}))
      solve))
