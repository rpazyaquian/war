(ns war.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [war.render :as r]))

(def temp-render-state
  [{:id :p1
    :deck []
    :card {:suit :hearts
           :face 9}}
   {:id :p2
    :deck []
    :card {:suit :spades
           :face 9}}])

(defn setup []
  (q/frame-rate 1)
  temp-render-state)

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 0 123 0)
  (r/render-state state))

(q/defsketch war
  :title "War"
  :size [640 480]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])
