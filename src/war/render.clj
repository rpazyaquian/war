(ns war.render
  (:require [quil.core :as q]))

(def spades-cards
  {2 "resources/sprites/cards/spades/spades-two.png"
   3 "resources/sprites/cards/spades/spades-three.png"
   4 "resources/sprites/cards/spades/spades-four.png"
   5 "resources/sprites/cards/spades/spades-five.png"
   6 "resources/sprites/cards/spades/spades-six.png"
   7 "resources/sprites/cards/spades/spades-seven.png"
   8 "resources/sprites/cards/spades/spades-eight.png"
   9 "resources/sprites/cards/spades/spades-nine.png"
   10 "resources/sprites/cards/spades/spades-ten.png"
   11 "resources/sprites/cards/spades/spades-jack.png"
   12 "resources/sprites/cards/spades/spades-queen.png"
   13 "resources/sprites/cards/spades/spades-king.png"
   14 "resources/sprites/cards/spades/spades-ace.png"})

(def hearts-cards
  {2 "resources/sprites/cards/hearts/hearts-two.png"
   3 "resources/sprites/cards/hearts/hearts-three.png"
   4 "resources/sprites/cards/hearts/hearts-four.png"
   5 "resources/sprites/cards/hearts/hearts-five.png"
   6 "resources/sprites/cards/hearts/hearts-six.png"
   7 "resources/sprites/cards/hearts/hearts-seven.png"
   8 "resources/sprites/cards/hearts/hearts-eight.png"
   9 "resources/sprites/cards/hearts/hearts-nine.png"
   10 "resources/sprites/cards/hearts/hearts-ten.png"
   11 "resources/sprites/cards/hearts/hearts-jack.png"
   12 "resources/sprites/cards/hearts/hearts-queen.png"
   13 "resources/sprites/cards/hearts/hearts-king.png"
   14 "resources/sprites/cards/hearts/hearts-ace.png"})

(def clubs-cards
  {2 "resources/sprites/cards/clubs/clubs-two.png"
   3 "resources/sprites/cards/clubs/clubs-three.png"
   4 "resources/sprites/cards/clubs/clubs-four.png"
   5 "resources/sprites/cards/clubs/clubs-five.png"
   6 "resources/sprites/cards/clubs/clubs-six.png"
   7 "resources/sprites/cards/clubs/clubs-seven.png"
   8 "resources/sprites/cards/clubs/clubs-eight.png"
   9 "resources/sprites/cards/clubs/clubs-nine.png"
   10 "resources/sprites/cards/clubs/clubs-ten.png"
   11 "resources/sprites/cards/clubs/clubs-jack.png"
   12 "resources/sprites/cards/clubs/clubs-queen.png"
   13 "resources/sprites/cards/clubs/clubs-king.png"
   14 "resources/sprites/cards/clubs/clubs-ace.png"})

(def diamonds-cards
  {2 "resources/sprites/cards/diamonds/diamonds-two.png"
   3 "resources/sprites/cards/diamonds/diamonds-three.png"
   4 "resources/sprites/cards/diamonds/diamonds-four.png"
   5 "resources/sprites/cards/diamonds/diamonds-five.png"
   6 "resources/sprites/cards/diamonds/diamonds-six.png"
   7 "resources/sprites/cards/diamonds/diamonds-seven.png"
   8 "resources/sprites/cards/diamonds/diamonds-eight.png"
   9 "resources/sprites/cards/diamonds/diamonds-nine.png"
   10 "resources/sprites/cards/diamonds/diamonds-ten.png"
   11 "resources/sprites/cards/diamonds/diamonds-jack.png"
   12 "resources/sprites/cards/diamonds/diamonds-queen.png"
   13 "resources/sprites/cards/diamonds/diamonds-king.png"
   14 "resources/sprites/cards/diamonds/diamonds-ace.png"})

(def positions
  {:p1 {:deck {:x 30
               :y 30}
        :card {:x 230
               :y 30}}
   :p2 {:deck {:x 430
               :y 300}
        :card {:x 230
               :y 300}}})

(defn image-for-card [{:keys [suit face]} images]
  (get-in images [suit face]))

(defn image-for-deck [deck images]
  (if (empty? deck)
    (:base images)
    (:back images)))

(defn render-card [id card images]
  (let [{:keys [x y]} (get-in positions [id :card])]
    (q/image (image-for-card card images) x y 100 140)))

(defn render-deck [id deck images]
  (let [{:keys [x y]} (get-in positions [id :deck])]
    (q/image (image-for-deck deck images) x y 100 140)))

(defn render-player [player images]
  (let [{:keys [deck card id]} player]
    (render-deck id deck images)
    (if (not (nil? card))
      (render-card id card images))))

(defn render-players [{:keys [players images] :as game-state}]
  (dorun (map #(render-player % images) players))
  game-state)

(defn render-state [game-state]
  (-> game-state
      (render-players)))
