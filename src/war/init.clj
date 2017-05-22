(ns war.init
  (:require [clojure.math.combinatorics :as combo]
            [quil.core :as q]
            [war.card :as c]
            [war.player :as p]
            [war.deck :as d]
            [war.game :as g]))

; initialization and configuration,
; as well as default game states,
; resource URIs, etc.
; this should be EDN, but i'll do that later

(def sprite-path "resources/sprites/cards")

(def suits
  [:spades :hearts :diamonds :clubs])

(def faces
  [2 3 4 5 6 7 8 9 10 11 12 13 14])

(defn with-path [[suit face]]
  (let [img-path (str sprite-path "/" suit "/" face ".png")]
    [suit face img-path]))

(defn load-sprite [[suit face img-path]]
  [suit face (q/load-image img-path)])

(defn new-deck []
  (->> (combo/cartesian-product suits faces)
       (map with-path)
       (map load-sprite)
       (map #(apply c/->Card %))))

(def player-positions
  {:p1 {:deck {:x 30
               :y 30}
        :card {:x 230
               :y 30}}
   :p2 {:deck {:x 430
               :y 300}
        :card {:x 230
               :y 300}}})

; create a deck, shuffle it  
; make new players with the deck
; create a new game with the players

(defn make-decks [[d1 d2]]
  [(d/->Deck d1 :sprite 30 30)
   (d/->Deck d2 :sprite 230 30)])

(defn make-players [shuffled-deck]
  (let [[d1 d2] (make-decks (partition 26 shuffled-deck))]
    [(p/->Player :p1 d1 [])
     (p/->Player :p2 d2 [])]))

(defn new-game []
  (let [shuffled-deck (-> (new-deck)
                          (shuffle))
        players (make-players shuffled-deck)]
    (g/->Game players :game-over "hello world")))
