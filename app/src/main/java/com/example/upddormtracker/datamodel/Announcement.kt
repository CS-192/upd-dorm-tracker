package com.example.upddormtracker.datamodel

data class Announcement(
    var subject: String,
    var details: String,
    var date: String,
    var time: String,
    val dorm: String)
