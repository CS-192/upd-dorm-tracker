package com.example.upddormtracker.ui.entryexitlogs

import java.util.Date

data class EntryExitLog(
    val date: Date,
    val studentNumber: String,
    val entryOrExit: String
)