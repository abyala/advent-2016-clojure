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
