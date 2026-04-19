package org.dsqrwym.homechat.network

object ApiConfig {
    const val BASE_PATH: String = "/home-chat"
    const val BASE_URL: String = "https://api.dsqrwym.es$BASE_PATH"

    //const val BASE_URL: String = "http://127.0.0.1:3030$BASE_PATH"
    const val SERVER_HOST: String = "api.dsqrwym.es"

    //const val SERVER_HOST: String = "127.0.0.1"
    const val SERVER_PORT: Int = 443
    //const val SERVER_PORT: Int = 3030


    // WebSocket 路径（不带主机和端口）
    const val WS_PATH = "$BASE_PATH/chat"

    // 超时配置（供 HttpClient 使用）
    const val CONNECT_TIMEOUT_MILLIS = 10_000L
    const val REQUEST_TIMEOUT_MILLIS = 38_000L
    const val ENABLE_LOGGING = true
}