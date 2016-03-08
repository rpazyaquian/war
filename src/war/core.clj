(ns war.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [war.render :as r]
            [war.game :as g]))

(def temp-initial-state
  {:players [{:id :p1
              :deck [{:suit :hearts
                      :face 9}]
              :card nil}
             {:id :p2
              :deck [{:suit :spades
                      :face 7}]
              :card nil}]
   :message ""
   :images {:back "resources/sprites/cards/back.png"
            :base "resources/sprites/cards/base.png"
            :spades r/spades-cards
            :hearts r/hearts-cards
            :diamonds r/diamonds-cards
            :clubs r/clubs-cards}})

(defn load-image [m k v]
  (assoc m k (q/load-image v)))

(defn load-images [{:keys [base back spades hearts diamonds clubs]}]
  {:back (q/load-image back)
   :base (q/load-image base)
   :spades (reduce-kv load-image {} spades)
   :hearts (reduce-kv load-image {} hearts)
   :diamonds (reduce-kv load-image {} diamonds)
   :clubs (reduce-kv load-image {} clubs)})

(defn setup []
  (q/frame-rate 30)
  (-> temp-initial-state
      (update :images load-images)))

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 0 123 0)
  (r/render-state state))

(defn mouse-clicked [old-state {:keys [x y button]}]
  (g/tick-state old-state))

(q/defsketch war
  :title "War"
  :size [640 480]
  :setup setup
  :update update-state
  :draw draw-state
  :mouse-clicked mouse-clicked
  :middleware [m/fun-mode])
