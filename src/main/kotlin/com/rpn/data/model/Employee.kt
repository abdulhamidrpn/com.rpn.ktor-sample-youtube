package com.rpn.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Employee(
    val name: String,
    val surName: String,
    val year: String,
    @BsonId
    val id: String = ObjectId().toString()
)
