#!/bin/bash -eu
# Run every closure example, one `clojure -M:<alias>` invocation each,
# with a header before each example (the old `make all`).
EXAMPLES=(
	basic
	counter
	adder
	memoize
	once
	accumulator
	private-state
	compose
	loop-capture
	memo-fib
)
for example in "${EXAMPLES[@]}"; do
	echo "=== ${example} ==="
	clojure -M:"${example}"
	echo
done
