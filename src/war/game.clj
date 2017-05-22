(ns war.game
  (:require [war.render :as r]
            [war.player :as p]
            [quil.core :as q]))

(defrecord Game [players state message]
  r/Render
  (render [this]
          (doseq (map p/render (:players this)))
          (q/text (:message this) 0 0)))
