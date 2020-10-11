(ns advent-2016-clojure.day5
  (:require [clojure.string :as str])
  (:import (java.security MessageDigest)))

(defn md5 [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn is-valid-hash? [s] (str/starts-with? s "00000"))

(defn next-password-char [s]
  (when (is-valid-hash? s) (get s 5)))

(defn get-password [door-id]
  (->> (range)
       (map #(str door-id %))
       (map md5)
       (keep next-password-char)
       (take 8)
       (apply str)))

(defn part1 [input]
  (get-password input))
