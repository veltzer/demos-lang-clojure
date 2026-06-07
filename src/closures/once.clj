(ns closures.once)

;; Example 5: A "run only once" guard implemented with a closure.
;; The closure captures a `called?` flag; subsequent calls are no-ops.

(defn once
  [f]
  (let [called? (atom false)
        result  (atom nil)]
    (fn [& args]
      (when-not @called?
        (reset! result (apply f args))
        (reset! called? true))
      @result)))

(defn -main
  [& _args]
  (let [init (once (fn [] (println "  initializing...") :ready))]
    (println (init)) ;; prints "initializing..." then :ready
    (println (init)) ;; just :ready, no side effect
    (println (init)))) ;; just :ready
