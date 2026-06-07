(ns closures.loop-capture)

;; Example 9: Each closure captures its own binding.
;; A common gotcha in other languages is that closures created in a loop
;; all share one mutable variable. In Clojure, locals are immutable, so
;; each iteration's closure captures its own value of `i`.

(defn make-multipliers
  []
  ;; one closure per i; each remembers its own i
  (mapv (fn [i]
          (fn [x] (* i x)))
        (range 1 6)))

(defn -main
  [& _args]
  (let [mults (make-multipliers)]
    ;; mults is [*1 *2 *3 *4 *5]
    (doseq [[i f] (map vector (range 1 6) mults)]
      (println (str "x" i " of 10 = " (f 10))))
    ;; Each closure kept its own captured i:
    (println (map #(% 10) mults)))) ;; (10 20 30 40 50)
