#!/usr/bin/env bash
# Turns compiler and Gradle errors from a build log into GitHub annotations,
# so they show on the run summary without opening the full log.
# Usage: report-errors.sh <log file>
log="$1"
[ -f "$log" ] || exit 0

enc() { sed -e 's/%/%25/g' | awk 'BEGIN{ORS="%0A"} {print}'; }

# Kotlin, Swift and linker errors, one annotation each (GitHub shows up to 10 per step).
grep -E '^e: |error: |^ld: |Undefined symbol|Unresolved reference' "$log" \
  | grep -v 'error: Build input file cannot be found' \
  | sort -u | head -8 \
  | while IFS= read -r line; do echo "::error::$(printf '%s' "$line" | cut -c1-900)"; done

# Gradle's "What went wrong" block.
block=$(awk '/What went wrong:/{f=1} /\* Try:/{f=0} f' "$log" | head -40)
if [ -n "$block" ]; then
  echo "::error title=Gradle::$(printf '%s\n' "$block" | enc)"
fi
