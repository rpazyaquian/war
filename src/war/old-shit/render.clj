; (ns war.render
;   [:require [quil.core :as q]])

; (defn text-render-war-card [war-card]
;   (let [{:keys [face suit]} war-card]
;     (str face " of " suit)))

; (defn text-render-player [player]
;   (str (:name player) ":\n\t"
;        (when (:war-card player)
;          (str "Played " (text-render-war-card (:war-card player)) "\n\t"))
;        (count (:deck player)) " cards in deck\n\t"
;        (count (:wagers player)) " cards wagered\n\t"
;        (if (:war-card player)
;          "1 card on field\n"
;          "No card on field\n")))

; (defn text-render [game-state]
;   (when-not (:game-over game-state)
;     (println "\n====== TURN " (:turns game-state) (if (= (:phase game-state) :war) "(WAR!)" "") " ======\n")
;     (let [player-statuses (map text-render-player (sort-by :order (:players game-state)))]
;       (doall (map println player-statuses)))))

; (defn render [game-state]
;   (q/fill 0 0 0)
;   (q/text (str "TURN " (:turns game-state)) 200 200))
