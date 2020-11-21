(ns advent-2016-clojure.day12
  (:require [advent-2016-clojure.assembunny :as bunny]))

(defn part1 [input]
  (bunny/computer-final-register-a input bunny/empty-registers))

(defn part2 [input]
  (bunny/computer-final-register-a input (assoc bunny/empty-registers "c" 1)))