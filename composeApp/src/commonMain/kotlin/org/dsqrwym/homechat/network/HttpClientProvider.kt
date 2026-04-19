package org.dsqrwym.homechat.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Expected provider for a platform-specific singleton [HttpClient].
 * 平台特定的单例 [HttpClient] 提供者（expect 声明，由各平台实现）。
 */
expect object HttpClientProvider {
    val client: HttpClient
}


/**
 * Install common Ktor plugins and defaults shared across platforms.
 * 安装各平台通用的 Ktor 插件与默认配置。
 */
internal fun HttpClientConfig<*>.installCommonPlugins() {
    // JSON serialization; ignore unknown fields to be forward-compatible
    // JSON 序列化；忽略未知字段以提升前向兼容性
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // 忽略未知字段
            explicitNulls = false // 如果为null 就不向JSON输出字段 等于JavaScript的undefined
            encodeDefaults = true // 默认值也写入JSON
        })
    }
    // Timeouts to avoid hanging requests
    // 配置连接与请求超时，避免请求长时间挂起
    install(HttpTimeout) {
        connectTimeoutMillis = ApiConfig.CONNECT_TIMEOUT_MILLIS
        requestTimeoutMillis = ApiConfig.REQUEST_TIMEOUT_MILLIS
    }
    if (ApiConfig.ENABLE_LOGGING) {
        install(Logging) { level = LogLevel.ALL }
    }
    install(WebSockets)
}