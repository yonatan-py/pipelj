(ns pipes.core-test
  (:require [midje.sweet :refer [fact]]
            [pipes.core :refer [pipe _pipe]]))

(fact
  (pipe
    (fn [_nil] "foo") | (fn [data]
                          (println "first function:" data)
                          (str data data)) | #(clojure.string/capitalize %)) => "Foofoo")
