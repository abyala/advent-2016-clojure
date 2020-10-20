(ns advent-2016-clojure.day11
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;Format of a state:
; {:elevator 1, :floors {1 ({:type :chip :element "hydrogen"}, {:type :chip :element "lithium"}),
;                        2 ({:type :generator :element "hydrogen"})
;                        3 ({:type :generator :element "lithium"})}}

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

(defn state-summary [state]
  {:elevator (state :elevator)
   :elements (->> state
                  :floors
                  (map (fn [[num items]] (map (fn [item] {:element (:element item)
                                                          (:type item) num})
                                              items)))
                  (apply concat)
                  (group-by :element)
                  vals
                  (map #(apply merge %))
                  (map #(dissoc % :element))
                  (sort #(compare [(:chip %1) (:generator %1)]
                                  [(:chip %2) (:generator %2)])))})

(defn steps-to-move-up [state]
  ; Candidates (sort by distance)
  ; ALL summaries EVER found - whether processed or not, don't want to see it again
  (loop [candidates #{{:state state :distance 0}}
         summaries-known #{(state-summary state)}]
    (let [chosen-candidate (closest-candidate candidates)
          {next-state :state distance :distance} chosen-candidate]
      (if (final-state? next-state)
        distance
        (let [candidate-pairs (->> next-state
                                   possible-next-states
                                   (map #(vector (state-summary %) %))
                                   (into {})
                                   (filter #(not (contains? summaries-known (first %)))))
              new-summaries (set (map first candidate-pairs))
              new-candidates (->> candidate-pairs
                                  (map second)
                                  (map (fn [c] {:state c :distance (inc distance)}))
                                  set)]
          (recur (apply merge (disj candidates chosen-candidate) new-candidates)
                 (apply merge summaries-known new-summaries)))))))

(defn element-of-type [t e] {:type t :element e})
(defn parse-line [line]
  (let [chips (when-let [matches (re-seq #"(\w+)-compatible microchip" line)]
                (map #(element-of-type :chip (second %)) matches))
        gens (when-let [matches (re-seq #"(\w+) generator" line)]
               (map #(element-of-type :generator (second %)) matches))]
    (reduce (partial apply merge) () (list chips gens))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-line)
       (map-indexed #(vector (inc %1) %2))
       (into {})))

(defn initialize-state [input]
  {:elevator 1 :floors (parse-input input)})

(defn part1 [input]
  (-> input initialize-state steps-to-move-up))

(defn part2 [input]
  (->> input
       initialize-state
       (add-to-floor (list {:type :chip :element "elerium"}
                           {:type :generator :element "elerium"}
                           {:type :chip :element "dilithium"}
                           {:type :generator :element "dilithium"}) 1)
       steps-to-move-up))