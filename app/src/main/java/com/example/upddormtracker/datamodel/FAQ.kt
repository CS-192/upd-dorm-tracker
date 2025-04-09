package com.example.upddormtracker.datamodel

data class FAQ(
    var documentId: String,
    var question: String,
    var answer: String,
    val dorm: String,
)
