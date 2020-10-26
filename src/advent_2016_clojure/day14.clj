(ns advent-2016-clojure.day14
  (:require[advent-2016-clojure.utils :as utils :only [md5]]
            [clojure.set :as set]))

(defn salted-hashes
  ([salt] (salted-hashes salt 0))
  ([salt n] (lazy-seq (cons (->> n
                                 (str salt)
                                 (utils/md5))
                            (salted-hashes salt (inc n))))))

(defn repeats-of-length [length word]
  (->> (range (- (count word) (dec length)))
       (map #(subs word % (+ % length)))
       (filter #(apply = %))
       (map first)
       set))

(defn repeat-tuple [idx hash]
  [idx (repeats-of-length 3 hash) (repeats-of-length 5 hash)])

(defn key? [triples others]
  (->> others
       (map #(set/intersection triples (nth % 2)))
       (some not-empty)
       some?))

(defn windowed-hashes
  ; for now, assume the size is always 1000 so I can have simple argument sizes
  ([salt]
   (let [s (salted-hashes salt)
         window (map-indexed #(repeat-tuple %1 %2) s)]
     (windowed-hashes window 0)))
  ; Window is of form [ [idx triples fifths] ]
  ([window offset]
   (if (key? ((first window) 1) (->> window (take 1001) rest))
     (lazy-seq (cons offset (windowed-hashes (rest window) (inc offset))))
     (lazy-seq (windowed-hashes (rest window) (inc offset))))))

(defn nth-hash [n salt]
  (last (take n (windowed-hashes salt))))

(defn part1 [salt]
  (nth-hash 64 salt))