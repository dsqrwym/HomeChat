package org.dsqrwym.homechat.plugins

import io.ktor.server.application.*
import org.dsqrwym.homechat.modules.chatModule
import org.dsqrwym.homechat.services.ChatService
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(chatModule)
    }
}