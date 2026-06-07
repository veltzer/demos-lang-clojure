(ns closures.adder)

;; Example 3: Closures as a factory of specialized functions (currying flavor).
;; `adder` captures `x` and returns a function that adds `x` to its argument.

(defn adder
  [x]
  (fn [y]
    (+ x y)))

(defn -main
  [& _args]
  (let [add5  (adder 5)
        add10 (adder 10)]
    (println (add5 1))   ;; 6
    (println (add5 100)) ;; 105
    (println (add10 1))  ;; 11
    ;; map over a collection with a closed-over increment
    (println (map (adder 3) [1 2 3 4])))) ;; (4 5 6 7)
