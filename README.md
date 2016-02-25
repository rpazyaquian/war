# war

A Quil sketch designed to ... well, that part is up to you.

## Usage

LightTable - open `core.clj` and press `Ctrl+Shift+Enter` to evaluate the file.

Emacs - run cider, open `core.clj` and press `C-c C-k` to evaluate the file.

REPL - run `(require 'war.core)`.

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## NOTES

1. define data
  a. what does a player look like?
    i. a deck of cards to draw from - "reserve deck" - stack
    ii. a deck of cards on field (zero or more cards) - "play deck" - stack
  b. what does a card look like?
    i. a type (spades, hearts, diamonds, clubs), with matching "Suit" string representation - enum 
    ii. a value (1-14), with matching "Face" string representation - int
  c. what does a game look like?
    i. two players - seq
    ii. number of turns - int
    iii. state ("peace", "war", "game over") - enum
      - game starts in "peace" mode, where each player has an initial reserve deck and no cards in play deck
      - **state is calculated based on state of players**
      - PROPOSAL: split game state up into "overall state" and "battle state", two states controlling the state of the game at large (peace, war, game over) and state of players battling (battling/not-battling), maybe?
2. render data
  a. render players
    i. player 1 occupies left half of field, player 2 occupies right half of field
    ii. each player has a reserve deck and a play deck, which are represented by a face down card and topmost card on deck respectively
  b. render cards
    i. reserve deck represented by dashed outline if empty, facedown card if not empty
    ii. play deck represented by dashed outline if empty, faceup card corresponding to topmost card if not empty
  c. render game
    i. display turn # and state ("peace", "war", "game over (player x wins)" at top of screen
3. respond to input
  a. mouse click
    i. advance game state (placeholder: print string to console)
4. define operations on data
  a. player
    i. play card
      - if at peace: take topmost card on reserve deck, place face up on play deck
      - if at war: n times, do: take topmost card on reserve deck, place face down on play deck - THEN: take topmost card on reserve deck, place face up on play deck
        - ensure handling less than n cards in reserve deck
    ii. win
      - when a player wins, they add their opponent's play deck to their own play deck, **shuffles their play deck**, then adds play deck to the **bottom** of their reserve pile
    iii. lose
      - when a player loses, they give up their play deck to the other player - i.e., it's wiped.
    - question: should win/lose operations be handled by the *game* instead?
  b. game
    i. tick
      - if one player has no reserve cards or play cards, other player wins, set game state to game over.
      - if players have empty play decks:
        - if game state is "peace":
          - both players add 1 card to play deck
        - if game state is "war":
          - both players add n+1 cards to play deck
      - if players do not have empty play decks:
        - resolve battle, and award winnings
    ii. maybe all the data crunching and manipulation should be operations on the game, at first?
    ii. consider creating macro for altering players


### BOTTOMUP STUFF

```
#{{:name "Player 1"
   :order 1}
  {:name "Player 2"
   :order 2}}

; --init state-->

{:players {:p1 {:reserve-deck [{:suit :spades :face 2}
                               {:suit :spades :face 3}
                               {:suit :spades :face 4}
                               {:suit :spades :face 5}
                               {:suit :spades :face 6}
                               {:suit :spades :face 7}
                               {:suit :spades :face 8}
                               {:suit :spades :face 9}
                               {:suit :spades :face 10}
                               {:suit :spades :face 11}
                               {:suit :spades :face 12}
                               {:suit :spades :face 13}
                               {:suit :spades :face 14}
                               {:suit :hearts :face 2}
                               {:suit :hearts :face 3}
                               {:suit :hearts :face 4}
                               {:suit :hearts :face 5}
                               {:suit :hearts :face 6}
                               {:suit :hearts :face 7}
                               {:suit :hearts :face 8}
                               {:suit :hearts :face 9}
                               {:suit :hearts :face 10}
                               {:suit :hearts :face 11}
                               {:suit :hearts :face 12}
                               {:suit :hearts :face 13}
                               {:suit :hearts :face 14}]
                :play-deck []
                :name "Player 1"}
           :p2 {:reserve-deck [{:suit :clubs :face 2}
                               {:suit :clubs :face 3}
                               {:suit :clubs :face 4}
                               {:suit :clubs :face 5}
                               {:suit :clubs :face 6}
                               {:suit :clubs :face 7}
                               {:suit :clubs :face 8}
                               {:suit :clubs :face 9}
                               {:suit :clubs :face 10}
                               {:suit :clubs :face 11}
                               {:suit :clubs :face 12}
                               {:suit :clubs :face 13}
                               {:suit :clubs :face 14}
                               {:suit :diamonds :face 2}
                               {:suit :diamonds :face 3}
                               {:suit :diamonds :face 4}
                               {:suit :diamonds :face 5}
                               {:suit :diamonds :face 6}
                               {:suit :diamonds :face 7}
                               {:suit :diamonds :face 8}
                               {:suit :diamonds :face 9}
                               {:suit :diamonds :face 10}
                               {:suit :diamonds :face 11}
                               {:suit :diamonds :face 12}
                               {:suit :diamonds :face 13}
                               {:suit :diamonds :face 14}]
                :play-deck []
                :name "Player 2"}}}
```
