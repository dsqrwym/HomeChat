# HomeChat

[English](./README.md) | [Español](./README.es.md)

HomeChat 是一个基于 Kotlin Multiplatform + Ktor 的轻量级实时文字聊天项目。

当前阶段：简易实时文字聊天。

未来方向：逐步演进为“家庭私密聊天”应用，重点关注安全、私密与亲友沟通场景。

## 项目结构

- `composeApp/`
  - Compose Multiplatform 客户端（Android / iOS / Desktop / Web）。
  - 主要 UI 与聊天交互逻辑。
- `server/`
  - 基于 Ktor 的 WebSocket 服务端，用于实时消息转发。
  - 含基础输入校验与防滥用保护。
- `shared/`
  - 跨平台共享模型与常量。
- `iosApp/`
  - iOS 原生入口工程，用于启动共享 Compose 应用。

## 运行方式

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

- 用 Xcode 打开 `iosApp/` 后运行。