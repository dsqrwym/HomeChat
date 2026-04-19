package org.dsqrwym.homechat

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.dsqrwym.homechat.plugins.configureKoin
import org.dsqrwym.homechat.plugins.configureValidation
import org.dsqrwym.homechat.plugins.configureWebSockets
import org.dsqrwym.homechat.routes.chatRoutes
import org.koin.ktor.ext.get

fun Application.module() {
    configureKoin()
    configureWebSockets()
    configureValidation()
    routing {
        route("/home-chat") {
            get("/") {
                call.respondText("Ktor: ${Greeting().greet()}")
            }

            chatRoutes(get())
        }
    }
}