(ns pipes.core-test
  (:require [midje.sweet :refer [fact]]
            [pipes.core :refer [pipe]]
            [clojure.string :as str]))

(fact "basic usage"
  (pipe
    (fn [] "foo") |
    #(str % %) |
    #(clojure.string/capitalize %))
  =>
  "Foofoo")

(fact "throws in case of an even number of forms"
  (try
    (macroexpand '(pipe
                   (fn [] "foo") |
                   #(str % %)
                   #(clojure.string/capitalize %)))
    (catch Exception exception
      (-> exception
          str
          (str/starts-with? "Syntax error macroexpanding pipe at")))) ; "pipe requires an odd number of forms"
  => true)

(fact "throws in case of non-pipe separators"
  (try
    (macroexpand '(pipe
                    (fn [] "foo") |
                    #(str % %) \
                    #(clojure.string/capitalize %)))
    (catch Exception exception
      (-> exception
          str
          (str/starts-with? "Syntax error macroexpanding pipe at")))) ; "even forms should be pipes (|)"
  => true)

(fact "single | form"
      (try
        (macroexpand '(pipe |))
        (catch Exception exception
          (-> exception
              str
              (str/starts-with? "Syntax error macroexpanding pipe at")))))

(fact "test form only"
  (let [f1 (fn [])
        f2 (fn [])
        f3 (fn [])]
    (macroexpand '(pipe f1 | f2 | f3))) => '(f3 (f2 (f1))))

(fact "single form"
      (let [f1 (fn [])]
        (macroexpand '(pipe f1))))

