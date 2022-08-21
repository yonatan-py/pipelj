(ns pipes.core)

(defn pipe* [result* functions*]
  (let [[function & functions] functions*
        result (list function result*)]
    (if (= (count functions) 0)
      result
      (pipe* result functions))))


(defmacro pipe [& forms]
  (let [actual-forms (take-nth 2 forms)
        pipes (->> forms (rest) (take-nth 2))
        [initial-function & functions] actual-forms]
    (if (even? (count forms))
      (throw (IllegalArgumentException.
               "pipe requires an odd number of forms")))
    (if (some #(-> % str (not= "|")) pipes)
      (throw (IllegalArgumentException.
               "event forms should be pipes (|)")))
    (pipe* (list initial-function) functions)))
