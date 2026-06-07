(ns closures.counter)

;; Example 2: Mutable state captured by a closure.
;; The atom `n` lives in the closure. Each call to the returned function
;; increments and returns it. Two counters are independent.

(defn make-counter
  []
  (let [n (atom 0)]
    (fn []
      (swap! n inc))))

(defn -main
  [& _args]
  (let [c1 (make-counter)
        c2 (make-counter)]
    (println (c1)) ;; 1
    (println (c1)) ;; 2
    (println (c1)) ;; 3
    (println (c2)) ;; 1  -- independent state
    (println (c1)))) ;; 4
