#!/bin/bash -eu
# Remove the Clojure CLI classpath cache (regenerated from deps.edn).
cd "$(dirname "$0")/.."
rm -rf .cpcache
