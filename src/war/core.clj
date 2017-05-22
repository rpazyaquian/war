(ns war.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [war.init :as i]))

(defn setup []
  (q/frame-rate 30)
  (i/new-game))

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 0 123 0))

(defn mouse-clicked [old-state {:keys [x y button]}]
  old-state)

(q/defsketch war
  :title "War"
  :size [640 480]
  :setup setup
  :update update-state
  :draw draw-state
  :mouse-clicked mouse-clicked
  :middleware [m/fun-mode])
