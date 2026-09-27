#!/usr/bin/env bash
# Optional: download TheApp for a portable AUT (does not change default local.properties).
# Your current ExpandTesting package/activity local setup stays untouched.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUT="${ROOT}/apps"
mkdir -p "$OUT"
VER="${THEAPP_VERSION:-v1.12.0}"
dest="${OUT}/TheApp.apk"
if [[ -f "$dest" ]]; then
  echo "OK (cached): $dest"
else
  curl -fL --retry 3 -o "$dest.partial" \
    "https://github.com/appium-pro/TheApp/releases/download/${VER}/TheApp.apk"
  mv "$dest.partial" "$dest"
  echo "OK: $dest"
fi
echo
echo "To try TheApp locally without breaking ExpandTesting defaults, run BDD with:"
echo "  mvn test -Pbdd -Dapp.path=${dest}"
echo "(only if your DriverFactory honors app.path — otherwise set app.path in a local override file)"
