(ns war.card
  (:require [war.render :as r]
            [quil.core :as q]))

(defrecord Card [suit face sprite]
  r/Render
  (render [this]
          (q/image (:sprite this) (:x this) (:y this) 100 140)))
