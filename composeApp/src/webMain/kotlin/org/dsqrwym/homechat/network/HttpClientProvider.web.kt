package org.dsqrwym.homechat.network

import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlin.js.ExperimentalWasmJsInterop

actual object HttpClientProvider {
    @OptIn(ExperimentalWasmJsInterop::class)
    actual val client: HttpClient by lazy {
        HttpClient(Js) {
            installCommonPlugins()
        }
    }
}