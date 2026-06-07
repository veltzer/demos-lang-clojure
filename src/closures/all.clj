(ns closures.all
  "Runs every closure example in sequence."
  (:require [closures.basic]
            [closures.counter]
            [closures.adder]
            [closures.memoize]
            [closures.once]
            [closures.accumulator]
            [closures.private-state]
            [closures.compose]
            [closures.loop-capture]
            [closures.memo-fib]))

(def examples
  [["basic"         closures.basic/-main]
   ["counter"       closures.counter/-main]
   ["adder"         closures.adder/-main]
   ["memoize"       closures.memoize/-main]
   ["once"          closures.once/-main]
   ["accumulator"   closures.accumulator/-main]
   ["private-state" closures.private-state/-main]
   ["compose"       closures.compose/-main]
   ["loop-capture"  closures.loop-capture/-main]
   ["memo-fib"      closures.memo-fib/-main]])

(defn -main
  [& _args]
  (doseq [[name main-fn] examples]
    (println (str "=== " name " ==="))
    (main-fn)
    (println)))
