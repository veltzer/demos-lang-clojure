#!/bin/bash -eu
# Run every closure example in sequence via the :all alias
# (a single JVM, closures.all calls each -main in turn).
clojure -M:all
