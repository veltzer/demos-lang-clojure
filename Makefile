# Makefile for the Clojure closure demos.
# Each example is a deps.edn alias run via `clojure -M:<alias>`.

CLOJURE:=clojure

EXAMPLES:=basic counter adder memoize once accumulator private-state compose loop-capture memo-fib

.PHONY: all
all: $(EXAMPLES)
	@true

# one phony target per example, e.g. `make counter`
.PHONY: $(EXAMPLES)
$(EXAMPLES):
	@echo "=== $@ ==="
	$(CLOJURE) -M:$@
	@echo

# run every example in sequence via the :all alias
.PHONY: run-all
run-all:
	$(CLOJURE) -M:all

.PHONY: help
help:
	@echo "Targets:"
	@echo "  make all       - run every example, one per make target"
	@echo "  make run-all   - run every example via the :all alias"
	@echo "  make <example> - run a single example, one of:"
	@echo "                   $(EXAMPLES)"

.PHONY: clean
clean:
	rm -rf .cpcache
