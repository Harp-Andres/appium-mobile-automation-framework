#!/usr/bin/env bash
# Downloads TheApp APK for local / self-hosted Appium runs.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUT="${ROOT}/apps"
mkdir -p "$OUT"
VER="${THEAPP_VERSION:-v1.12.0}"
DEST="${OUT}/TheApp.apk"
URL="https://github.com/appium-pro/TheApp/releases/download/${VER}/TheApp.apk"

if [[ -f "$DEST" ]]; then
  echo "OK (cached): $DEST"
  exit 0
fi

echo "Downloading TheApp.apk (${VER})..."
curl -fL --retry 3 -o "${DEST}.partial" "$URL"
mv "${DEST}.partial" "$DEST"
echo "OK: $DEST"
