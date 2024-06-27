package com.rpn.routes

import com.rpn.data.createEmployeeOrUpdateEmployeeForId
import com.rpn.data.deleteEmployeeForId
import com.rpn.data.getAllEmployee
import com.rpn.data.getEmployeeForId
import com.rpn.data.model.Employee
import com.rpn.data.requests.DeleteEmployeeRequest
import com.rpn.data.requests.EmployeeRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.employeeRoutes() {

    route("/get-all-employee") {
        get {
            val employees = getAllEmployee()
            call.respond(
                HttpStatusCode.OK,
                employees
            )
        }
    }

    route("/get-employee") {
        get {
            val employeeId = call.receive<EmployeeRequest>().id
            val employee = getEmployeeForId(employeeId)
            employee?.let {
                call.respond(
                    HttpStatusCode.OK,
                    it
                )
            } ?: call.respond(
                HttpStatusCode.OK,
                "There is no Employee with this id."
            )
        }
    }

    route("/create-update-employee") {
        post {
            val request = try {
                call.receive<Employee>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (createEmployeeOrUpdateEmployeeForId(employee = request)) {
                call.respond(
                    HttpStatusCode.OK,
                    "Employee successfully created or updated"
                )
            } else {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }

    route("/delete-employee") {
        post {
            val request = try {
                call.receive<DeleteEmployeeRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (deleteEmployeeForId(employeeId = request.id)) {
                call.respond(
                    HttpStatusCode.OK,
                    "Employee successfully deleted"
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    "Employee not found"
                )
            }
        }
    }
}