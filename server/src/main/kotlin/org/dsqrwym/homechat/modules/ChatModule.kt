package org.dsqrwym.homechat.modules

import org.dsqrwym.homechat.services.ChatService
import org.koin.dsl.module

val chatModule = module {
    single<ChatService> {
        ChatService()
    }
}