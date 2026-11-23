#!/bin/bash -eu
# Install the Clojure CLI (`clojure`/`clj`) so that the run scripts work.
#
# The scripts in this repo run each example via `clojure -M:<alias>`,
# which requires the official Clojure command-line tools (not the older
# Debian/Ubuntu `clojure` apt package). This script installs those tools
# using the official linux-install script from clojure.org.
#
# Requirements: a JDK (java on PATH), plus curl, bash and sudo.

set -euo pipefail

# --- curl (needed to download the Clojure installer) -----------------------
if command -v curl >/dev/null 2>&1; then
	curl_path="$(command -v curl)"
	echo "[curl]    already installed (${curl_path}) -- skipping"
else
	if command -v apt-get >/dev/null 2>&1; then
		echo "[curl]    not found; installing curl..."
		sudo apt-get update -qq && sudo apt-get install -y curl
		hash -r 2>/dev/null || true
	fi
	if ! command -v curl >/dev/null 2>&1; then
		echo "error: curl not found on PATH and could not be installed automatically." >&2
		exit 1
	fi
	curl_path="$(command -v curl)"
	echo "[curl]    installed (${curl_path})"
fi

# --- java (Clojure needs a JDK to run) -------------------------------------
if command -v java >/dev/null 2>&1; then
	java_path="$(command -v java)"
	echo "[java]    already installed (${java_path}) -- skipping"
else
	if command -v apt-get >/dev/null 2>&1; then
		echo "[java]    not found; installing default-jdk..."
		sudo apt-get update -qq && sudo apt-get install -y default-jdk
		hash -r 2>/dev/null || true
	fi
	if ! command -v java >/dev/null 2>&1; then
		echo "error: java not found on PATH and could not be installed automatically." >&2
		echo "       Install a JDK first (e.g. sudo apt-get install -y default-jdk)." >&2
		exit 1
	fi
	java_path="$(command -v java)"
	echo "[java]    installed (${java_path})"
fi

# --- rlwrap (optional; used by the `clj` REPL wrapper) ---------------------
if command -v rlwrap >/dev/null 2>&1; then
	rlwrap_path="$(command -v rlwrap)"
	echo "[rlwrap]  already installed (${rlwrap_path}) -- skipping"
elif command -v apt-get >/dev/null 2>&1; then
	echo "[rlwrap]  not found; installing rlwrap (optional)..."
	sudo apt-get update -qq && sudo apt-get install -y rlwrap || true
fi

# --- clojure CLI -----------------------------------------------------------
if command -v clojure >/dev/null 2>&1; then
	clojure_path="$(command -v clojure)"
	echo "[clojure] already installed (${clojure_path}) -- skipping"
else
	INSTALLER="$(mktemp --suffix=-clojure-install.sh)"
	trap 'rm -f "$INSTALLER"' EXIT

	echo "[clojure] not found; downloading the official Clojure CLI installer..."
	curl -fsSL -o "${INSTALLER}" https://download.clojure.org/install/linux-install.sh
	chmod +x "${INSTALLER}"

	echo "[clojure] running the installer (installs to /usr/local, may prompt for sudo)..."
	sudo bash "${INSTALLER}"
	hash -r 2>/dev/null || true
	clojure_path="$(command -v clojure)"
	echo "[clojure] installed (${clojure_path})"
fi

# --- verify -----------------------------------------------------------------
hash -r 2>/dev/null || true
if command -v clojure >/dev/null 2>&1; then
	clojure_path="$(command -v clojure)"
	echo "success: ${clojure_path}"
	clojure --version
else
	echo "error: clojure still not on PATH after install." >&2
	exit 1
fi
