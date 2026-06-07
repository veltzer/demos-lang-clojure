(ns closures.memo-fib)

;; Example 10: A self-referential memoizing closure.
;; A recursive Fibonacci that caches results in a closed-over atom.
;; `fib` refers to itself by name (a `def`), and the cache lives in a
;; closure that the function body reads and writes -- turning an
;; exponential algorithm into a linear one.

(def fib
  (let [cache (atom {0 0N, 1 1N})]
    (fn fib [n]
      (or (@cache n)
          (let [result (+ (fib (- n 1))
                          (fib (- n 2)))]
            (swap! cache assoc n result)
            result)))))

(defn -main
  [& _args]
  (println "fib 10 =" (fib 10))    ;; 55
  (println "fib 20 =" (fib 20))    ;; 6765
  (println "fib 100 =" (fib 100))) ;; 354224848179261915075 (BigInt, instant thanks to memoization)
