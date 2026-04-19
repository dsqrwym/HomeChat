package org.dsqrwym.homechat.network

actual object HttpClientProvider {
    actual val client: HttpClient by lazy {
        HttpClient { installCommonPlugins() }
    }
}