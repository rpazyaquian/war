(ns war.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [war.render :as r]))

(def temp-render-state
  {:players [{:id :p1
              :deck []
              :card {:suit :hearts
                     :face 9}}
             {:id :p2
              :deck []
              :card {:suit :spades
                     :face 9}}]})

(defn setup []
  (q/frame-rate 1)
  temp-render-state)

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 0 123 0)
  (r/render-state state))

(defn mouse-clicked [old-state {:keys [x y button]}]
  (println (str "Clicked " button " button at (" x ", " y ")"))
  old-state)

; (defn mouse-clicked [old-state {:keys [x y button]}]
;   (g/tick old-state))

(q/defsketch war
  :title "War"
  :size [640 480]
  :setup setup
  :update update-state
  :draw draw-state
  :mouse-clicked mouse-clicked
  :middleware [m/fun-mode])
