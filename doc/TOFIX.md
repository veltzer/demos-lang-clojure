# TOFIX

Findings from a code scan on 2026-10-04.

## Medium

- `rsconstruct.toml:6` - nothing in the build checks or runs the eleven `src/closures/*.clj` files (only shellcheck, taplo, actionlint, rumdl are configured), so a broken namespace or a wrong expected-output comment ships green; add a Clojure checker (e.g. a `clj-kondo` processor in rsconstruct, or a processor that runs `clojure -M:all`) so the demos are verified.
- `src/closures/private_state.clj:12` - `withdraw` reads `@balance` and then calls `swap!` separately, a check-then-act race that can overdraw under concurrent calls, which undercuts an example about atoms; do the check inside the swap (e.g. `swap-vals!` with a function that refuses, or `compare-and-set!` loop) and throw based on its result.

## Low

- `rsconstruct.toml:7` - shellcheck `src_dirs = ["scripts", "src"]` includes `src/`, which holds only `.clj` files; drop `"src"` so the src_dirs list is precise.
- `src/closures/once.clj:11` - `@called?` is checked and then set in two steps, so two threads can both run `f`; either note in the comment that this guard is single-threaded only or use `compare-and-set!` on the flag.
- `README.md:42` - says checks are shellcheck and taplo only; update once a Clojure check is added (and mention actionlint/rumdl, which also run).
