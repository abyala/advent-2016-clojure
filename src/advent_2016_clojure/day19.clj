(ns advent-2016-clojure.day19)

; ALGORITHM:
; 1. Start with a vector of actual values [1 through n]
; 2. Keep vectors of each thieving elf and the elf it wants to steal from
;    a. Part 1 - map even indexes to nil
;    b. Part 2 - map based on original count + diff and 1 or 2
;    c. Ignore when target index goes above the actual count
;    d. After we have the indexes, map both thieves and victims to their values
; 3. Split the first parts (thieves) from the second (victims)
; 4. Determine the last theft from the first group
; 5. Make a set of all victims
; 6. Create a new vector of non-victims
; 7. Pivot subvectors up and including the last thief

(defn initial-elves [n] (vec (range 1 (inc n))))

(def neighbor-elf
  "Steal from your neighbor, so only the even indexes win."
  (fn [i _] (when (even? i) (inc i))))

(def halfway-elf
  "Steal from halfway across the circle. Victim is the sum of:
  - Index halfway across the room, rounded down
  - Add either (repeat +1/+2 for even totals, or +2/+1 for odd totals)
      After every two thefts, we skip over one victim (the +2 advancement)"
  (fn [i total]
    (+ (quot total 2)
       (* 3 (quot i 2))
       (cond (even? i) 0
             (even? total) 1
             :else 2))))

(defn find-thefts
  "Given a vector of elves, return a sequence of [thief victim]"
  [elves steal-fn]
  (let [num-elves (count elves)]
    (keep #(when-let [victim (steal-fn % num-elves)]
             (when (< victim num-elves)
               [(get elves %) (get elves victim)]))
          (range num-elves))))

(defn steal
  "Given a vector of elves and a thieving function, apply the function as much
  as possible, remove the victims, and rotate the vector to the next eligible thief."
  [elves f]
  (let [thefts (find-thefts elves f)
        thieves (map first thefts)
        victims (set (map second thefts))
        last-thief (last thieves)
        survivors (filterv #(not (contains? victims %))
                           elves)
        pivot (inc (first (keep-indexed #(when (= %2 last-thief) %1)
                                        survivors)))]
    (let [[a b] (split-at pivot survivors)]
      (into (vec b) (vec a)))))

(defn white-elephant-party
  "Set up the elves, indexing from 1 to num-elves, and keep stealing until one elf remains."
  [num-elves f]
  (loop [players (initial-elves num-elves)]
    (if (= 1 (count players))
      (first players)
      (recur (steal players f)))))

(defn part1 [num-elves]
  (white-elephant-party num-elves neighbor-elf))

(defn part2 [num-elves]
  (white-elephant-party num-elves halfway-elf))