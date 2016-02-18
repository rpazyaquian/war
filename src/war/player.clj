(ns war.player)

(defrecord Player [name order deck war-card last-war-card wagers])

(defn make-player [name order deck]
  (->Player name order deck nil nil []))  ; no one has any war cards or wagers at first

(defn play-war-card [player]
  (let [{:keys [deck]} player
        war-card-and-deck (split-at 1 deck)
        war-card (first (first war-card-and-deck))
        new-deck (last war-card-and-deck)]
    (-> player
        (assoc :war-card war-card)
        (assoc :last-war-card war-card)
        (assoc :deck new-deck))))

(defn war-card-to-wagers [player]
  (let [{:keys [war-card wagers]} player
        new-wagers (conj wagers war-card)]
    (-> player
        (assoc :war-card nil)
        (assoc :wagers new-wagers))))

(defn war-cards-to-wagers [players]
  (let [{:keys [winner loser]} players
        new-winner (war-card-to-wagers winner)
        new-loser (war-card-to-wagers loser)]
    (-> players
        (assoc :winner new-winner)
        (assoc :loser new-loser))))

(defn winner-gets-wagers [players]
  (let [{:keys [winner loser]} players
        winner-wagers (:wagers winner)
        loser-wagers (:wagers loser)
        new-winner (assoc winner :wagers winner-wagers)
        new-loser (assoc loser :wagers [])]
    (-> players
        (assoc :winner new-winner)
        (assoc :loser new-loser))))

(defn winner-shuffles-wagers [players]
  (let [winner (:winner players)
        winner-wagers (shuffle (:wagers winner))
        new-winner (assoc winner :wagers winner-wagers)]
    (-> players
        (assoc :winner new-winner))))

(defn winner-adds-wagers [players]
  (let [winner (:winner players)
        winner-wagers (:wagers winner)
        winner-deck (:deck winner)
        new-winner-deck (concat winner-deck winner-wagers)
        new-winner (-> winner
                       (assoc :deck new-winner-deck))]
    (-> players
        (assoc :winner new-winner))))

(defn wipe-winner-wagers [players]
  (let [winner (:winner players)
        new-winner (assoc winner :wagers [])]
    (-> players
        (assoc :winner new-winner))))

(defn go-to-war [player number-of-cards-to-bet]
  (let [{:keys [deck wagers]} player
        split-deck (split-at number-of-cards-to-bet deck)
        wagers-to-add (first split-deck)
        new-deck (last split-deck)]
    (-> player
        (assoc :deck new-deck)
        (assoc :wagers (concat wagers wagers-to-add)))))

(defn beats [winner loser]
  (let [players {:winner winner
                 :loser loser}]
    (-> players
        (war-cards-to-wagers)
        (winner-gets-wagers)
        (winner-shuffles-wagers)
        (winner-adds-wagers)
        (wipe-winner-wagers))))
