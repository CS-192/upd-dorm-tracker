package com.example.upddormtracker.ui.managedormers
import java.io.Serializable

data class Dormer(
    val docId : String = "",
    val studentNumber: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val birthday: String = "",
    val phoneNumber: String = "",
    val dorm: String = "",
    val sex: String = "",
    val roomNumber: String = "",
    val email: String = ""
): Serializable