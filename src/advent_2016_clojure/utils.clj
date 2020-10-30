(ns advent-2016-clojure.utils
  (:import (java.security MessageDigest)))

(defn abs [v]
  (if (neg? v) (- 0 v) v))

(defn md5
  "Converts a String to its hex md5"
  [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn xor
  "Clojure has no xor??? This will treat nil as false."
  [a b]
  (or (and (true? a) (not (true? b)))
      (and (true? b) (not (true? a)))))