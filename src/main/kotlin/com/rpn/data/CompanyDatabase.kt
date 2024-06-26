package com.rpn.data

import com.rpn.data.model.Employee
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo


private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("CompanyDatabase")

private val employees = database.getCollection<Employee>()

suspend fun getEmployeeForId(id: String): Employee? {
    return employees.findOneById(id)
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