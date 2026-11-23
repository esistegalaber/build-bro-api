#!/usr/bin/env bash
# Renders the API contract with Redocly (Redoc 3) into one self-contained page: target/openapi/redoc/index.html.
# Needs Node (npx); the Maven build does not. CI runs this after `./mvnw verify`, so the page is part of the
# `api-docs` artifact next to the html2 page.
set -euo pipefail
cd "$(dirname "$0")"

# No usage telemetry, no update nag
export REDOCLY_TELEMETRY=off
export REDOCLY_SUPPRESS_UPDATE_NOTICE=true

npx --yes @redocly/cli@2.82.0 build-docs src/main/resources/openapi/build-bro-api.yaml \
  --inlineBundle \
  --title "Build Bro API" \
  --output target/openapi/redoc/index.html
