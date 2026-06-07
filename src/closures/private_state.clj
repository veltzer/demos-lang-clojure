(ns closures.private-state)

;; Example 7: Encapsulation -- a closure returns a map of functions that
;; all share private state (a bank account). The balance is not directly
;; reachable from the outside; only via the returned operations.

(defn make-account
  [opening-balance]
  (let [balance (atom opening-balance)]
    {:deposit  (fn [amount] (swap! balance + amount))
     :withdraw (fn [amount]
                 (if (>= @balance amount)
                   (swap! balance - amount)
                   (throw (ex-info "Insufficient funds"
                                   {:balance @balance :requested amount}))))
     :balance  (fn [] @balance)}))

(defn -main
  [& _args]
  (let [{:keys [deposit withdraw balance]} (make-account 100)]
    (println "start:   " (balance))   ;; 100
    (deposit 50)
    (println "after +50:" (balance))  ;; 150
    (withdraw 30)
    (println "after -30:" (balance))  ;; 120
    (try
      (withdraw 1000)
      (catch clojure.lang.ExceptionInfo e
        (println "error:   " (ex-message e))))))
