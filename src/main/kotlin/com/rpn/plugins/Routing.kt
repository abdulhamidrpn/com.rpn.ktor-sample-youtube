package com.rpn.plugins

import com.rpn.routes.employeeRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        employeeRoutes()
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
