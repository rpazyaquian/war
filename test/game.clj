(ns war.gametest
  (:use midje.sweet))


; (def player-options
;   {:name "Player 1"
;    :side :left})

; (def initial-player
;   {:reserve-deck []
;    :play-deck []
;    :name "Player 1"
;    :side :left})

; (fact
;   (g/make-player player-options) => initial-player)

; (def deck
;   [{:suit :spades :face 2}
;    {:suit :spades :face 3}
;    {:suit :spades :face 4}
;    {:suit :spades :face 5}])

; (def player-with-deck
;   {:reserve-deck deck
;    :play-deck []
;    :name "Player 1"
;    :side :left})

; (fact 
;   (g/deal-deck initial-player deck) => player-with-deck)

; (def played-card
;   {:reserve-deck (rest deck)
;    :play-deck (vec (first deck))
;    :name "Player 1"
;    :side :left})

; (fact
;   (g/play-card player-with-deck 1) => played-card)

; (def game-options
;   #{{:name "Player 1"
;      :side :left}
;     {:name "Player 2"
;      :side :right }})

; (def initial-game-state
;   {:players #{{:reserve-deck []
;                :play-deck []
;                :name "Player 1"
;                :side :left}
;               {:reserve-deck []
;                :play-deck []
;                :name "Player 2"
;                :side :right}}})

; (fact
;   (g/initialize-game game-options) => initial-game-state)

; (def left-deck
;   [{:suit :spades :face 2}])

; (def right-deck
;   [{:suit :hearts :face 9}])

; (def pre-fight-state
;   {:players #{{:reserve-deck left-deck
;                :play-deck []
;                :name "Player 1"
;                :side :left}
;               {:reserve-deck right-deck
;                :play-deck []
;                :name "Player 2"
;                :side :right}}})

; (def post-fight-state
;   )
