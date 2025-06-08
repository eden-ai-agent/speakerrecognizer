#!/usr/bin/env sh
# Simplified gradle wrapper script
DIR="$(cd "$(dirname "$0")" && pwd)"
if [ -f "$DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
  java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
else
  echo "gradle-wrapper.jar missing; falling back to system gradle" >&2
  gradle "$@"
fi
