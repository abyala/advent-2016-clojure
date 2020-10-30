(ns advent-2016-clojure.day18_strings)

(def SAFE \.)
(def TRAP \^)

(defn next-line [line]
  (let [s (str SAFE line SAFE)]
    (->> (range (count line))
         (map #(if (= (get s %) (get s (+ % 2))) SAFE TRAP))
         (apply str))))

(defn trap-sequence [line]
  (lazy-seq (cons line (trap-sequence (next-line line)))))

(defn num-safe-tiles [line num-rows]
  (->> (trap-sequence line)
       (take num-rows)
       (map #(count (filter (partial = SAFE) %)))
       (apply +)))