(ns closures.memoize)

;; Example 4: Hand-rolled memoization using a closure over a cache atom.
;; (Clojure has a built-in `memoize`; this shows how it works underneath.)

(defn my-memoize
  [f]
  (let [cache (atom {})]
    (fn [& args]
      (if-let [hit (find @cache args)]
        (val hit)
        (let [result (apply f args)]
          (swap! cache assoc args result)
          result)))))

(defn slow-square
  [x]
  (println "  computing" x)
  (* x x))

(defn -main
  [& _args]
  (let [fast-square (my-memoize slow-square)]
    (println (fast-square 4))  ;; computes, prints 16
    (println (fast-square 4))  ;; cached, no "computing" line, prints 16
    (println (fast-square 5))  ;; computes, prints 25
    (println (fast-square 5)))) ;; cached, prints 25
