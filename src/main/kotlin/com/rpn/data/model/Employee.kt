package com.rpn.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Employee(
    @BsonId val id: String = ObjectId().toString(),  // MongoDB ID
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String ? = null,
    val position: String ? = null,
    val department: String ? = null,
    val dateOfBirth: String ? = null,  // ISO 8601 format (YYYY-MM-DD)
    val dateOfHire: String ? = null,  // ISO 8601 format (YYYY-MM-DD)
    val address: Address ? = null,
    val salary: Double ? = null,
    val isActive: Boolean = false
)

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
)

