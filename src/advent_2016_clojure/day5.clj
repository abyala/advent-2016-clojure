(ns advent-2016-clojure.day5
  (:require [clojure.string :as str])
  (:import (java.security MessageDigest)))

(defn md5 [s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn next-password-char [s]
  (when (str/starts-with? s "00000") (get s 5)))

(defn get-password [length door-id]
  (->> (range)
       (map #(str door-id %))
       (map md5)
       (keep next-password-char)
       (take length)
       (apply str)))

(defn part1 [input]
  (get-password 8 input))
