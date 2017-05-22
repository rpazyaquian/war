(ns war.deck
  (:require [war.render :as r]
            [quil.core :as q]))

(defrecord Deck [cards back-sprite base-sprite x y]
  r/Render
  (render [this]
          (if (empty? (:cards this))
            (q/image (:base-sprite this) (:x this) (:y this) 100 140)
            (q/image (:back-sprite this) (:x this) (:y this) 100 140))))
