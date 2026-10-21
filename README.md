# Daftar · دفتر

A private family health file for Android and iOS: records, medicines, reminders, voice entry in Arabic and English, and Claude insights. Built for one family, not for sale.

This repository is public so GitHub Actions can build the iOS app on macOS for free. **It holds code only.** Family data, keys and certificates never go in it.

## Status

Phase 0: an empty bilingual app that builds on CI for both platforms.

## Project layout

| Path | What it is |
| --- | --- |
| `shared/` | Kotlin Multiplatform module: all screens (Compose Multiplatform) and logic |
| `androidApp/` | Android entry point |
| `iosApp/project.yml` | XcodeGen spec; CI generates the Xcode project from it |
| `iosApp/iosApp/` | iOS entry point (SwiftUI host), icon, Arabic and English app name |
| `fastlane/Fastfile` | Signs the iOS app and uploads it to TestFlight |
| `.github/workflows/` | `android.yml` and `ios.yml` |

Versions: Kotlin 2.4.20, Compose Multiplatform 1.12.0, Android Gradle plugin 9.3.3, Gradle 9.6.0.

## Builds

- **Android:** every push builds a debug APK, saved for 7 days as a workflow artifact.
- **iOS without signing secrets:** every push builds the app for iPhone, unsigned, to prove it compiles.
- **iOS with signing secrets:** every push to `main` signs the app and uploads it to TestFlight. A scheduled run on the 1st of each month keeps a fresh build, since TestFlight builds expire after 90 days.

## Repository secrets

Add these under **Settings → Secrets and variables → Actions → New repository secret**. How to create each one on Windows: [docs/ios-signing.md](docs/ios-signing.md).

| Secret | Used for |
| --- | --- |
| `APPLE_TEAM_ID` | Your 10-character Apple team ID |
| `IOS_DIST_CERT_P12_BASE64` | Apple Distribution certificate and key, as base64 |
| `IOS_DIST_CERT_PASSWORD` | Password of that `.p12` file |
| `IOS_PROFILE_APP_BASE64` | App Store provisioning profile for `com.aelshal.daftar`, as base64 |
| `ASC_KEY_ID` | App Store Connect API key ID |
| `ASC_ISSUER_ID` | App Store Connect issuer ID |
| `ASC_KEY_P8_BASE64` | The API key `.p8` file, as base64 |
| `ANDROID_KEYSTORE_BASE64` | Optional. Release keystore, as base64 |
| `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD` | Optional. Keystore details |

GitHub does not pass secrets to pull requests from forks, so outside contributions can never sign or upload.

## Rules for this public repo

- No secrets, real names, health data or screenshots of real records in code, issues, commits or logs.
- Test data uses made-up people only.
- Signing files are listed in `.gitignore`; keep them out of the repo folder anyway.
