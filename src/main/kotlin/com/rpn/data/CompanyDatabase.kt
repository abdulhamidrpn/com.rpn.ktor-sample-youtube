package com.rpn.data

import com.rpn.data.model.Employee
import com.rpn.data.utils.demoEmployeeDatajson
import io.ktor.http.ContentType.Application.Json
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("CompanyDatabase")

private val employees = database.getCollection<Employee>()

suspend fun getAllEmployee(): List<Employee?> {
    return employees.find().toList()
}

suspend fun getEmployeeForId(id: String): Employee? {
    return employees.findOneById(id)
}

suspend fun importEmployeeFromJson(){

    val employees = Json.decodeFromString<List<Employee>>(demoEmployeeDatajson)
    employees.insertMany(employees)
}

suspend fun createEmployeeOrUpdateEmployeeForId(employee: Employee): Boolean {
    val employeeExist = employees.findOneById(employee.id) != null
    return if (employeeExist) {
        employees.updateOneById(employee.id, employee).wasAcknowledged()
    } else {
        val newEmployee = employee.copy(id = ObjectId().toString())
        employees.insertOne(newEmployee).wasAcknowledged()
    }
}

suspend fun deleteEmployeeForId(employeeId: String): Boolean {
    val employee = employees.findOne(Employee::id eq employeeId)
    return employee?.let {
        employees.deleteOneById(it.id).wasAcknowledged()
    } ?: false
}