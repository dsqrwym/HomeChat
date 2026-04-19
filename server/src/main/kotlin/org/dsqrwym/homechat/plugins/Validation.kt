package org.dsqrwym.homechat.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.dsqrwym.homechat.model.SendChatMessageRequest

@Serializable
data class ApiErrorResponse(
    val code: String,
    val message: String,
)

fun Application.configureValidation() {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    install(RequestValidation) {
        validate<SendChatMessageRequest> { body ->
            when {
                body.text.isBlank() -> ValidationResult.Invalid("text cannot be blank")
                else -> ValidationResult.Valid
            }
        }
    }

    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiErrorResponse(
                    code = "VALIDATION_FAILED",
                    message = cause.reasons.joinToString(separator = "; "),
                ),
            )
        }
    }
}