(ns pipes.core)

(defn pipe* [result* functions*]
  (let [[function & functions] functions*
        result (list function result*)]
    (if (= (count functions) 0)
      result
      (pipe* result functions))))


(defmacro pipe [& forms]
  ; TODO: validate
  (let [actual-forms (take-nth 2 forms)]
    (pipe* 'nil actual-forms)))
