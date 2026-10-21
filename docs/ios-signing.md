# iOS signing from Windows

Everything here works without a Mac. You need a paid Apple Developer Program membership, Git for Windows (it includes Git Bash and OpenSSL) and PowerShell.

Keep the files you create in a private folder **outside** this repository, for example `C:\Users\you\daftar-signing\`. Back that folder up somewhere private: if you lose the `.key` file you must make a new certificate.

## 1. Find your team ID

[developer.apple.com/account](https://developer.apple.com/account) → **Membership details** → **Team ID** (10 characters). Save it as the secret `APPLE_TEAM_ID`.

## 2. Create the distribution certificate

Open **Git Bash** in your signing folder:

```bash
openssl genrsa -out daftar_dist.key 2048
openssl req -new -key daftar_dist.key -out daftar_dist.csr -subj "/emailAddress=YOUR_APPLE_ID_EMAIL/CN=Daftar Distribution/C=EG"
```

In the developer portal: **Certificates** → **+** → **Apple Distribution** → upload `daftar_dist.csr` → download `distribution.cer` into the same folder. Then:

```bash
openssl x509 -inform DER -in distribution.cer -out distribution.pem
openssl pkcs12 -export -inkey daftar_dist.key -in distribution.pem -out daftar_dist.p12 \
  -keypbe PBE-SHA1-3DES -certpbe PBE-SHA1-3DES -macalg sha1
```

It asks for an export password. Save that password as the secret `IOS_DIST_CERT_PASSWORD`. The older encryption flags matter: macOS cannot import a `.p12` made with OpenSSL 3's default settings.

## 3. Register the app ID

**Identifiers** → **+** → **App IDs** → **App** → description `Daftar`, explicit bundle ID `com.aelshal.daftar`. No capabilities are needed yet; HealthKit and App Groups get added in later phases, and then the profile in step 4 is made again.

## 4. Create the provisioning profile

**Profiles** → **+** → **App Store Connect** (under Distribution) → app ID `com.aelshal.daftar` → the certificate from step 2 → name it `Daftar App Store` → download `Daftar_App_Store.mobileprovision`.

## 5. Create the app in App Store Connect

[appstoreconnect.apple.com](https://appstoreconnect.apple.com) → **Apps** → **+** → **New App**: platform iOS, bundle ID `com.aelshal.daftar`, SKU `daftar`. The name must be unique across the whole App Store, so `Daftar` may be taken; use something like `Daftar Family`. This name is only shown in App Store Connect and TestFlight. The home screen still says Daftar or دفتر.

The app is never submitted for review or released. TestFlight internal testing needs no review.

## 6. Create the App Store Connect API key

App Store Connect → **Users and Access** → **Integrations** → **App Store Connect API** → **Team Keys** → **+**, access **App Manager**. Download the `.p8` file (Apple lets you download it only once). Note the **Key ID** and the **Issuer ID** shown above the list.

## 7. Turn files into base64 and add the secrets

In **PowerShell**, in your signing folder, run each line, then paste the clipboard into the matching GitHub secret:

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("daftar_dist.p12")) | Set-Clipboard           # IOS_DIST_CERT_P12_BASE64
[Convert]::ToBase64String([IO.File]::ReadAllBytes("Daftar_App_Store.mobileprovision")) | Set-Clipboard  # IOS_PROFILE_APP_BASE64
[Convert]::ToBase64String([IO.File]::ReadAllBytes("AuthKey_XXXXXXXXXX.p8")) | Set-Clipboard      # ASC_KEY_P8_BASE64
```

Also add `ASC_KEY_ID` and `ASC_ISSUER_ID` as plain text.

GitHub: repository → **Settings** → **Secrets and variables** → **Actions** → **New repository secret**.

## 8. Run it

Push to `main`, or open **Actions** → **iOS** → **Run workflow**. When it finishes, the build appears in App Store Connect → **TestFlight** after Apple processes it (usually a few minutes to an hour).

## 9. Install on the family's iPhones

Each person needs to be an App Store Connect user to be an internal tester: **Users and Access** → **+**, give them the **Developer** role (or **Marketing**, which can't change the app). Then **TestFlight** → **Internal Testing** → **+** a group → add them. They install the **TestFlight** app from the App Store and accept the invite.

## When something fails

| Error mentions | Usually means |
| --- | --- |
| `MAC verification failed` or import errors | The `.p12` was exported without the flags in step 2 |
| `No profile for team ... matching` | The profile was made for a different bundle ID or certificate |
| `Authentication credentials are missing or invalid` | Wrong key ID, issuer ID, or the `.p8` was not base64-encoded |
| `The bundle version must be higher` | A build with that number already exists. Use **Run workflow** for a new run; **Re-run jobs** reuses the old number |
