(ns day2)

;  1 red, 2 green, 6 blue
; [[1 "red"] [2 "green"] [6 "blue"]]
(defn parse-game [str]
  (let [
        items (clojure.string/split str #", " )
        num-value-pairs (map #(let [a (println "$" %) [num color] (clojure.string/split % #"\s")]
                                (vector color (Integer/parseInt num))) items)
        ]
    (into {} num-value-pairs)))

(= {"red" 1 "green" 2 "blue" 6} (parse-game "1 red, 2 green, 6 blue"))

; Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
(defn parse-line [str]
  (let [
        [name values] (clojure.string/split str #": ")
        id (Integer/parseInt (second (clojure.string/split name #"\s")))
        games (map parse-game (clojure.string/split values #"; "))]
    {:id id :values games}))

(= { :id 1 :values [{"blue" 3 "red" 4}, {"red" 1 "green" 2 "blue" 6}, {"green" 2}]} (parse-line "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))

;{"blue" 3 "red" 4}
(defn valid-game [game-vals] (and
                               (<= (get game-vals "blue" 0) 14)
                               (<= (get game-vals "red" 0) 12)
                               (<= (get game-vals "green" 0) 13)))

(true? (valid-game {"blue" 14 "red" 12 "green" 13}))
(true? (valid-game {"blue" 5 "red" 10 "green" 14}))
(true? (valid-game {"blue" 14 "red" 12}))

(def input (slurp "resources/input2.txt"))
(def input-lines (clojure.string/split input #"\r\n"))
(def games (map parse-line input-lines))
(def valid-games (filter
                   (fn [{id :id values :values}]
                     (println values)
                     (every? valid-game values))
                   games))
(def valid-ids (map #(:id %) valid-games))

; Add up the valid ids to produce the first answer
(reduce + valid-ids)


; Day 2 question

;[{"blue" 3 "red" 4}, {"red" 1 "green" 2 "blue" 6}]
(defn process-game-vals [values] (reduce (fn [total val] (merge-with max total val)) {"red" 0 "green" 0 "blue" 0} values))
(= {"blue" 6 "red" 4 "green" 2} (process-game-vals [{"blue" 3 "red" 4}, {"red" 1 "green" 2 "blue" 6}]))
(def max-cubes-games (map (fn [{id :id values :values}] (process-game-vals values)) games))
(reduce + 0 (map #(apply * (vals %)) max-cubes-games))