(ns war.card
  (:require [clojure.math.combinatorics :as c]))

(defrecord Card [suit color face value])

(def suits
  [{:suit "Spades"
    :color :black}
   {:suit "Hearts"
    :color :red}])

(def faces
  [{:face "Two"
    :value 2}
   {:face "Three"
    :value 3}
   {:face "Four"
    :value 4}
   {:face "Five"
    :value 5}
   {:face "Six"
    :value 6}
   {:face "Seven"
    :value 7}
   {:face "Eight"
    :value 8}
   {:face "Nine"
    :value 9}
   {:face "Ten"
    :value 10}
   {:face "Jack"
    :value 11}
   {:face "Queen"
    :value 12}
   {:face "King"
    :value 13}
   {:face "Ace"
    :value 14}])

(def combos (c/cartesian-product suits faces))

(def suits-faces
  (map #(apply merge %) combos))

(defn make-card [suit-face]
  (let [{:keys [suit color face value]} suit-face]
    (->Card suit color face value)))

(def deck
  (map make-card suits-faces))

(defn cut-deck [deck]
  (->> deck
      (shuffle)
      (partition (/ (count combos) 2))))
