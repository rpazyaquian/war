(ns war.player
  (:require [war.render :as r]
            [war.deck :as d]
            [war.card :as c]
            [quil.core :as q]))

(defrecord Player [id deck cards]
  r/Render
  (render [this]
          (d/render (:deck this))
          (doseq (map c/render (:cards this)))))
