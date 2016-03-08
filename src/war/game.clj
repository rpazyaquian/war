(ns war.game
  (:require [clojure.math.combinatorics :as combo]))

; player decks should be QUEUES!
; clojure.lang.PersistentQueue, clojure.lang.PersistentQueue/EMPTY, etc

(def suits [:spades :hearts :diamonds :clubs])

(def faces [2 3 4 5 6 7 8 9 10 11 12 13 14])

(defn make-card [suit face]
  {:suit suit
   :face face})

(def full-deck (map #(make-card %1 %2) (combo/cartesian-product suits faces)))

(defn temp-second-state [game-state]
  (assoc game-state :players [{:id :p1
                               :deck []
                               :card {:suit :hearts
                                      :face 9}}
                              {:id :p2
                               :deck []
                               :card {:suit :spades
                                      :face 7}}]))

(def temp-third-state
  {:players [{:id :p1
              :deck [{:suit :hearts
                     :face 9}
                     {:suit :spades
                     :face 7}]
              :card nil}
             {:id :p2
              :deck []
              :card nil}]
   :message "Player 1 wins!"})

(defn tick-state [game-state]
  (temp-second-state game-state))
