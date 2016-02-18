(ns war.player)

(defrecord Player [name order deck war-card wagers])

(defn make-player [name order deck]
  (->Player name order deck nil []))  ; no one has any war cards or wagers at first

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
        (assoc :winner winner)
        (assoc :loser loser))))

(defn winner-gets-wagers [players]
  todo-lol)

(defn winner-shuffles-wagers [players]
  todo-lol)

(defn winner-adds-wagers [players]
  todo-lol)

(defn wipe-winner-wagers [players]
  todo-lol)

(defn beats [winner loser]
  ; winner adds war card to wagers
  ; loser adds war card to wagers
  ; winner adds loser's wagers to their wagers
  ; loser loses wages
  ; winner shuffles wagers
  ; winner adds wagers to bottom of deck
  ; wipe winner's wages
  (let [players {:winner winner
                 :loser loser}]
    (-> players
        (war-cards-to-wagers)
        (winner-gets-wagers)
        (winner-shuffles-wagers)
        (winner-adds-wagers)
        (wipe-winner-wagers))))