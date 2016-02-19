(ns war.game
  (:require [war.card :as c]
            [war.player :as p]))

(def initial-game-state
  (let [player-decks (c/cut-deck c/deck)
        player-1 (p/make-player "Player 1" 1 (first player-decks))
        player-2 (p/make-player "Player 2" 2 (last player-decks))]
    {:players [player-1 player-2]
     :turns 0
     :phase :peace
     :game-over false}))

(defn war-cards-on-field? [game-state]
  (every? p/has-war-card? (:players game-state)))

(defn find-winner-loser [players]
  (let [[player-1 player-2] players]
    (if (> (:value (:war-card player-1)) (:value (:war-card player-2)))
      [player-1 player-2]
      [player-2 player-1])))

(defn wipe-wagers-and-war-cards [game-state]
  (-> game-state
      (assoc :players (map p/wipe (:players game-state)))))

(defn award-winnings [game-state winnings]
  (let [[winner loser] (find-winner-loser (:players game-state))
        happy-winner (p/award-winnings winner winnings)]
    (-> game-state
        (assoc :players [happy-winner loser]))))

(defn get-winnings [game-state]
  (let [wagers (flatten (map :wagers (:players game-state)))
        war-cards (map :war-card (:players game-state))]
  (concat wagers war-cards)))

(defn resolve-winner [game-state]
  (let [winnings (get-winnings game-state)]
    (-> game-state
        (award-winnings winnings))))

(defn play-war-cards [game-state]
  (-> game-state
      (assoc :players (map p/play-war-card (:players game-state)))))

(defn players-tied? [game-state]
  (let [war-cards (map :war-card (:players game-state))
        war-card-values (map :value war-cards)]
    (not (apply distinct? war-card-values))))

(defn check-if-player-lost [game-state]
  (let [fail-players (filter #(= 0 (count (:deck %))) (:players game-state))]
    (if (> 0 (count fail-players))
      (-> game-state
          (assoc :game-over true))
      (-> game-state
          (assoc :game-over false)))))

(defn start-or-continue-war [game-state]
  (if (not (= :war (:phase game-state)))
    (-> game-state
        (assoc :players (map #(p/wager 3 %) (:players game-state)))
        (assoc :phase :war))
    (-> game-state
        (assoc :players (map #(p/add-wager 3 %) (:players game-state))))))

(defn resolve-war-cards [game-state]
  (if (players-tied? game-state)
    (start-or-continue-war game-state)
    (-> game-state
        (resolve-winner)
        (wipe-wagers-and-war-cards)
        (check-if-player-lost)
        (assoc :phase :peace))))

(defn add-turn [game-state]
  (-> game-state
      (assoc :turns (inc (:turns game-state)))))

(defn print-deck [player]
  (let [deck (:deck player)
        top-card (first deck)]
    (println (:name player) " has "(count deck) " cards, top card: " (str (:face top-card) " of " (:suit top-card)))))

(defn print-decks [players]
  (doall (map print-deck (sort-by :order players))))

(defn tick [game-state]
  (print-decks (:players game-state))
  (if (and (war-cards-on-field? game-state) (not (:game-over game-state)))
    (resolve-war-cards game-state)
    (-> game-state
        (play-war-cards)
        (add-turn))))
