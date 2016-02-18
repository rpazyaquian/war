(ns war.game
  (:require [war.card :as c]
            [war.player :as p]))

; declaration of game state

(defn cut-deck [deck]
  (->> deck
      (shuffle)
      (partition 26)))

(def initial-game-state
  (let [player-decks (cut-deck c/deck)
        player-1 (p/make-player "Player 1" 1 (first player-decks))
        player-2 (p/make-player "Player 2" 2 (last player-decks))]
    {:player-1 player-1
     :player-2 player-2}))

; to play war:
; 0. start with an initial game state.

; 1. each player takes the top most card on their deck,
; and plays it as their war card.
; 2. if one player has a card higher than the other,
; that player takes the other player's war card,
; and their own war card,
; shuffles the new deck,
; and adds it to the bottom of their deck.
; 3. if both cards are the same value,
; then it's war!
; each player adds the topmost three cards on their deck to their wagers.
; then, they add their current war card to the wager.
; then, they play another war card.
; go to step 2 and repeat until someone wins.

(defn play-war-cards [game-state]
  (let [{:keys [player-1 player-2]} game-state
        new-player-1 (p/play-war-card player-1)
        new-player-2 (p/play-war-card player-2)]
    (-> game-state
        (assoc :player-1 new-player-1)
        (assoc :player-2 new-player-2))))

(defn resolve-war-cards [game-state]
  (let [{:keys [player-1 player-2]} game-state
        war-card-1 (:war-card player-1)
        war-card-2 (:war-card player-2)]
    (cond
      (> (:value war-card-1) (:value war-card-2)) (player-1-wins game-state)
      (< (:value war-card-1) (:value war-card-2)) (player-2-wins game-state)
      (= (:value war-card-1) (:value war-card-2)) (start-war game-state))))

(defn player-1-wins [game-state]
  (let [{:keys [player-1 player-2]} game-state
        new-players (p/beats player-1 player-2)
        {:keys [new-player-1 new-player-2]} new-players]
    (-> game-state
        (assoc :player-1 new-player-1)
        (assoc :player-2 new-player-2))))

(defn player-2-wins [game-state]
  (let [{:keys [player-1 player-2]} game-state
        new-players (p/beats player-2 player-1)
        {:keys [new-player-1 new-player-2]} new-players]
    (-> game-state
        (assoc :player-1 new-player-1)
        (assoc :player-2 new-player-2))))

(defn start-war [game-state]
  (let [{:keys [player-1 player-2]} game-state
        new-player-1 (p/go-to-war player-1 3)
        new-player-2 (p/go-to-war player-2 3)]
    (-> game-state
        (assoc :player-1 new-player-1)
        (assoc :player-2 new-player-2))))

(defn tick [game-state]
  ; first, have each player play a war card.
  ; then, resolve the war card match.
  ; that specific function will determine whether one player takes winnings,
  ; or if each player puts down 3 cards in case of war.   
  (-> game-state
      (play-war-cards)
      (resolve-war-cards)))
