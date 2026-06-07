(ns closures.basic)

;; Example 1: The most basic closure.
;; `make-greeter` returns a function that "closes over" the `greeting`
;; argument. The returned function remembers `greeting` even after
;; `make-greeter` has returned.

(defn make-greeter
  [greeting]
  (fn [name]
    (str greeting ", " name "!")))

(defn -main
  [& _args]
  (let [hello (make-greeter "Hello")
        howdy (make-greeter "Howdy")]
    (println (hello "Alice"))   ;; Hello, Alice!
    (println (hello "Bob"))     ;; Hello, Bob!
    (println (howdy "Carol")))) ;; Howdy, Carol!
