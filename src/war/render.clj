(ns war.render)

(defprotocol Render
  "Rendering protocol"
  (render [_] "Render this entity"))
