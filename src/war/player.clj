(ns war.player)

(defrecord Player [name order deck war-card last-war-card wagers])

(defn make-player [name order deck]
  (->Player name order deck nil nil []))  ; no one has any war cards or wagers at first

(defn award-winnings [player winnings]
  (-> player
      (assoc :deck (concat (:deck player) (shuffle winnings)))))

(defn wager [n-cards player]
  (let [{:keys [deck war-card]} player
        [wagers new-deck] (split-at n-cards deck)]
    (-> player
        (assoc :wagers (conj wagers war-card))
        (assoc :deck new-deck)
        (assoc :war-card nil))))

(defn add-wager [n-cards player]
  (let [{:keys [deck war-card]} player
        [wagers new-deck] (split-at n-cards deck)]
    (-> player
        (assoc :wagers (conj (concat wagers (:wagers player)) war-card))
        (assoc :deck new-deck)
        (assoc :war-card nil))))

(defn wipe [player]
  (-> player
      (assoc :war-card nil)
      (assoc :wagers [])))

(defn has-war-card? [player]
  (:war-card player))

(defn play-war-card [player]
  (let [{:keys [deck]} player
        war-card-and-deck (split-at 1 deck)
        war-card (ffirst war-card-and-deck)
        new-deck (last war-card-and-deck)]
    (-> player
        (assoc :war-card war-card)
        (assoc :last-war-card war-card)
        (assoc :deck new-deck))))
