(ns pipes.core-test
  (:require [midje.sweet :refer [fact]]
            [pipes.core :refer [pipe _pipe]]))

(fact
  (pipe
    (fn [] "foo") |
    #(str % %) |
    #(clojure.string/capitalize %))
  =>
  "Foofoo")
