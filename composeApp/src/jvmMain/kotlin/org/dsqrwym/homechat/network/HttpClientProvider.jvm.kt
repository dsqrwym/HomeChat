package org.dsqrwym.homechat.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

actual object HttpClientProvider {
    actual val client: HttpClient by lazy {
        HttpClient(CIO) { installCommonPlugins() }
    }
}