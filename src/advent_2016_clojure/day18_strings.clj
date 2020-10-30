(ns advent-2016-clojure.day18_strings)

(defn next-line [line]
  (let [s (str \. line \.)]
    (apply str (map #(if (= (get s %)
                            (get s (+ 2 %)))
                       \. \^)
                    (range (count line))))))

(defn trap-sequence [line]
  (lazy-seq (cons line (trap-sequence (next-line line)))))

(defn num-safe-tiles [line num-rows]
  (->> line
       trap-sequence
       (take num-rows)
       (map #(count (filter (partial = \.) %)))
       (apply +)))