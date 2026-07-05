#!/bin/bash -eu
# Install the Clojure CLI (`clojure`/`clj`) so that `make` works.
#
# The Makefile in this repo runs each example via `clojure -M:<alias>`,
# which requires the official Clojure command-line tools (not the older
# Debian/Ubuntu `clojure` apt package). This script installs those tools
# using the official linux-install script from clojure.org.
#
# Requirements: a JDK (java on PATH), plus curl, bash and sudo.

set -euo pipefail

# --- already installed? -----------------------------------------------------
if command -v clojure >/dev/null 2>&1; then
	echo "clojure already installed: $(command -v clojure)"
	clojure --version || true
	exit 0
fi

# --- sanity checks ----------------------------------------------------------
if ! command -v curl >/dev/null 2>&1; then
	echo "error: curl not found on PATH." >&2
	exit 1
fi

# Clojure needs a JDK to run. Install one if java is missing.
if ! command -v java >/dev/null 2>&1; then
	if command -v apt-get >/dev/null 2>&1; then
		echo "java not found; installing default-jdk..."
		sudo apt-get update -qq && sudo apt-get install -y default-jdk
		hash -r 2>/dev/null || true
	fi
	if ! command -v java >/dev/null 2>&1; then
		echo "error: java not found on PATH and could not be installed automatically." >&2
		echo "       Install a JDK first (e.g. sudo apt-get install -y default-jdk)." >&2
		exit 1
	fi
fi

# rlwrap is an optional dependency used by the `clj` REPL wrapper.
if ! command -v rlwrap >/dev/null 2>&1 && command -v apt-get >/dev/null 2>&1; then
	echo "installing rlwrap (optional, used by clj)..."
	sudo apt-get update -qq && sudo apt-get install -y rlwrap || true
fi

# --- install the official Clojure CLI --------------------------------------
INSTALLER="$(mktemp --suffix=-clojure-install.sh)"
trap 'rm -f "$INSTALLER"' EXIT

echo "downloading the official Clojure CLI installer..."
curl -fsSL -o "$INSTALLER" https://download.clojure.org/install/linux-install.sh
chmod +x "$INSTALLER"

echo "running the installer (installs to /usr/local, may prompt for sudo)..."
sudo bash "$INSTALLER"

# --- verify -----------------------------------------------------------------
hash -r 2>/dev/null || true
if command -v clojure >/dev/null 2>&1; then
	echo "success: $(command -v clojure)"
	clojure --version
else
	echo "error: clojure still not on PATH after install." >&2
	exit 1
fi
