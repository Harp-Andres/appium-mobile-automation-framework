#!/usr/bin/env bash
# Download TheApp APK for local BDD (see local.properties app.path).
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
echo "Run BDD against a local emulator/device:"
echo "  mvn test -Pbdd -Drun.mobile.tests=true -Denv=local"
