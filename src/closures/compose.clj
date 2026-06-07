(ns closures.compose)

;; Example 8: Building higher-order functions with closures.
;; `pipeline` captures a sequence of fns and returns one fn that threads
;; a value through all of them, left to right.

(defn pipeline
  [& fns]
  (fn [x]
    (reduce (fn [acc f] (f acc)) x fns)))

(defn -main
  [& _args]
  (let [process (pipeline inc
                          #(* % 2)
                          #(- % 3))]
    ;; ((x+1)*2)-3
    (println (process 5))   ;; ((5+1)*2)-3 = 9
    (println (process 10))  ;; ((10+1)*2)-3 = 19
    (println (map process [0 1 2])))) ;; (-1 1 3)
