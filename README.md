# demos-lang-clojure

Demos and examples for the Clojure programming language.

## Closures

Ten closure examples live under `src/closures/`. Each is a self-contained
namespace with a `-main`, wired to a `deps.edn` alias so it can be run with
the Clojure CLI:

```sh
clojure -M:basic          # most basic closure (capture an argument)
clojure -M:counter        # mutable state via a captured atom
clojure -M:adder          # function factory / partial application
clojure -M:memoize        # hand-rolled memoization over a cache atom
clojure -M:once           # run-only-once guard
clojure -M:accumulator    # running-total accumulator generator
clojure -M:private-state  # encapsulated bank account (private state)
clojure -M:compose        # build a pipeline higher-order function
clojure -M:loop-capture   # each closure captures its own binding
clojure -M:memo-fib       # self-referential memoizing fibonacci
```

Run all ten in sequence with a single alias:

```sh
clojure -M:all
```

### Makefile

A `Makefile` wraps the same commands:

```sh
make help        # list targets
make all         # run every example (one make target each)
make run-all     # run every example via the :all alias
make counter     # run a single example
make clean       # remove the .cpcache directory
```
