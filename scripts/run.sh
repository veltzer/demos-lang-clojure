#!/bin/bash -eu
# Run a single closure example by its deps.edn alias,
# e.g. `scripts/run.sh counter`. See deps.edn for the list of aliases.
if [ $# -ne 1 ]; then
	echo "usage: $0 <example>" >&2
	exit 1
fi
clojure -M:"$1"
