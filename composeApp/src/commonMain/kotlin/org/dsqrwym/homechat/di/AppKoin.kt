package org.dsqrwym.homechat.di

import org.dsqrwym.homechat.data.chat.ChatApi
import org.dsqrwym.homechat.data.chat.ChatRepository
import org.dsqrwym.homechat.network.HttpClientProvider
import org.dsqrwym.homechat.ui.viewmodel.ChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ChatApi> { ChatApi(HttpClientProvider.client) }
    single<ChatRepository> { ChatRepository(get()) }
    viewModel<ChatViewModel> { ChatViewModel(get()) }
}