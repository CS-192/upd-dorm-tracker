package com.example.upddormtracker.datamodel


data class Request(
    var documentId: String,
    var timestamp: String,
    var type: String,
    var dorm: String,
    var name: String,)

