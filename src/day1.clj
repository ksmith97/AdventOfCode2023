(ns day1)

;; Part 1
(def input (slurp "resources/input1.txt"))
(def input-lines (clojure.string/split input #"\s+"))
(def numbers-str-1 (map #(list (re-find #"\d" %) (re-find #"\d" (clojure.string/reverse %))) input-lines))
(def numbers-1 (map (fn [[x y]] (Integer/parseInt (str x y))) numbers-str-1))
;; Answer
(reduce + numbers-1)

;; Part 2

(def numbers-arr ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"])
(def reversed-nums (map clojure.string/reverse numbers-arr))
(defn find-num [str numbers-arr]
  (let [
        first-num (re-find #"\d" str)
        num-idx (if (nil? first-num) 999999 (clojure.string/index-of str first-num))
        first-str (re-find (re-pattern (clojure.string/join "|" numbers-arr)) str)
        str-idx (if (nil? first-str) 999999 (clojure.string/index-of str first-str))
        str-num (.indexOf numbers-arr first-str)]
    (do
      ;;(println str first-num num-idx first-str str-idx str-num)
      (cond
        (nil? first-num) (+ 1 str-num)
        (nil? first-str) (Integer/parseInt first-num)
        (< num-idx str-idx) (Integer/parseInt first-num)
        :else (+ 1 str-num)
        ))))
(def numbers-str (map #(list (find-num % numbers-arr) (find-num (clojure.string/reverse %) reversed-nums)) input-lines))
(def numbers (map (fn [[x y]] (Integer/parseInt (str x y))) numbers-str))
;; Answer
(reduce + numbers)

;;(find-num (clojure.string/reverse "2njsevenszzsfltconesixhsflpbpd") reversed-nums)
;(println numbers-str)