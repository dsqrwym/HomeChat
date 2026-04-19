# HomeChat

[中文](./README.zh.md) | [Español](./README.es.md)

HomeChat is a lightweight real-time text chat project built with Kotlin Multiplatform + Ktor.

Current stage: simple real-time text chat.

Future direction: evolve into a private family chat application focused on secure and intimate communication.

## Project Structure

- `composeApp/`
  - Compose Multiplatform client app (Android / iOS / Desktop / Web).
  - Main UI and chat interaction logic.
- `server/`
  - Ktor-based WebSocket server for real-time message forwarding.
  - Includes basic input validation and anti-abuse protections.
- `shared/`
  - Cross-platform shared models/constants.
- `iosApp/`
  - Native iOS entry project for launching the shared Compose app.

## Run

### Android

```powershell
.\gradlew.bat :composeApp:assembleDebug
```

### Desktop (JVM)

```powershell
.\gradlew.bat :composeApp:run
```

### Server

```powershell
.\gradlew.bat :server:run
```

### Web (Wasm)

```powershell
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

### Web (JS)

```powershell
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

### iOS

- Open `iosApp/` in Xcode and run.