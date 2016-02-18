(ns war.game
  (:require [war.card :as c]
            [war.player :as p]))

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

(defn play-war-cards [game-state]
  (println "players, play your cards")
  (let [{:keys [player-1 player-2]} game-state
        new-player-1 (p/play-war-card player-1)
        new-player-2 (p/play-war-card player-2)]
    (-> game-state
        (assoc :player-1 new-player-1)
        (assoc :player-2 new-player-2))))

(defn player-1-wins [game-state]
  (println "player 1 won")
  (let [{:keys [player-1 player-2]} game-state
        new-players (p/beats player-1 player-2)
        {:keys [winner loser]} new-players]
    (-> game-state
        (assoc :player-1 winner)
        (assoc :player-2 loser))))

(defn player-2-wins [game-state]
  (println "player 2 won")
  (let [{:keys [player-1 player-2]} game-state
        new-players (p/beats player-2 player-1)
        {:keys [winner loser]} new-players]
    (-> game-state
        (assoc :player-1 loser)
        (assoc :player-2 winner))))

(defn start-war [game-state]
  (println "war started")
  (let [{:keys [player-1 player-2]} game-state
        new-player-1 (p/go-to-war player-1 3)
        new-player-2 (p/go-to-war player-2 3)]
    (-> game-state
        (assoc :player-1 new-player-1)
        (assoc :player-2 new-player-2))))

(defn resolve-war-cards [game-state]
  (println "let's see who won...")
  (let [{:keys [player-1 player-2]} game-state
        war-card-1 (:war-card player-1)
        war-card-2 (:war-card player-2)]
    (cond
      (> (:value war-card-1) (:value war-card-2)) (player-1-wins game-state)
      (< (:value war-card-1) (:value war-card-2)) (player-2-wins game-state)
      (= (:value war-card-1) (:value war-card-2)) (start-war game-state))))

(defn tick [game-state]
  (-> game-state
      (play-war-cards)
      (resolve-war-cards)))
