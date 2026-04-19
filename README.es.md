# HomeChat

[English](./README.md) | [中文](./README.zh.md)

HomeChat es un proyecto ligero de chat de texto en tiempo real construido con Kotlin Multiplatform + Ktor.

Etapa actual: chat de texto en tiempo real sencillo.

Dirección futura: evolucionar hacia una aplicación de chat privado familiar, enfocada en comunicación íntima y segura.

## Estructura del proyecto

- `composeApp/`
  - Aplicación cliente Compose Multiplatform (Android / iOS / Desktop / Web).
  - Lógica principal de UI e interacción de chat.
- `server/`
  - Servidor WebSocket basado en Ktor para reenvío de mensajes en tiempo real.
  - Incluye validación básica de entrada y protección antiabuso.
- `shared/`
  - Modelos y constantes compartidos entre plataformas.
- `iosApp/`
  - Proyecto de entrada nativo para iOS para iniciar la app Compose compartida.

## Ejecución

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

- Abre `iosApp/` con Xcode y ejecútalo.