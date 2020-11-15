(ns advent-2016-clojure.day23
  (:require [advent-2016-clojure.assembunny :as bunny]))

(defn part1 [input]
  (bunny/solve input (assoc bunny/empty-registers "a" 7)))

(defn part2 [input]
  (bunny/solve input (assoc bunny/empty-registers "a" 12)))