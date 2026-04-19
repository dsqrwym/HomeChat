package org.dsqrwym.homechat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.dsqrwym.homechat.di.appModule
import org.dsqrwym.homechat.ui.screen.ChatScreen
import org.dsqrwym.theme.MyMaterialTheme
import org.dsqrwym.theme.miSansNormalTypography
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
            Box(Modifier.safeContentPadding()) {
                ChatScreen()
            }
        }
    })
}