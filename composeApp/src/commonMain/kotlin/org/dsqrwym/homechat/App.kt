package org.dsqrwym.homechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.dsqrwym.homechat.di.appModule
import org.dsqrwym.homechat.ui.screen.ChatScreen
import org.dsqrwym.homechat.theme.MyMaterialTheme
import org.dsqrwym.homechat.theme.miSansNormalTypography
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(configuration = koinConfiguration(declaration = { modules(appModule) }), content = {
        MyMaterialTheme(
            typography = miSansNormalTypography(),
            shapes = MaterialTheme.shapes,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.systemBars)
            ) {
                ChatScreen()
            }
        }
    })
}