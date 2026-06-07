(ns closures.accumulator)

;; Example 6: An accumulator generator (Paul Graham's classic).
;; Returns a function that keeps a running total across calls.

(defn make-accumulator
  [init]
  (let [total (atom init)]
    (fn [n]
      (swap! total + n))))

(defn -main
  [& _args]
  (let [acc (make-accumulator 100)]
    (println (acc 10))  ;; 110
    (println (acc 10))  ;; 120
    (println (acc 5))   ;; 125
    ;; a fresh accumulator starts over
    (let [acc2 (make-accumulator 0)]
      (println (acc2 1))   ;; 1
      (println (acc2 2))))) ;; 3
