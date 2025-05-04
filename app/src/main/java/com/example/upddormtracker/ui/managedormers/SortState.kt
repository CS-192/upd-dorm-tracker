package com.example.upddormtracker.ui.managedormers

enum class SortState {
    NONE,
    ASCENDING,
    DESCENDING;

    fun next(): SortState {
        return when (this) {
            NONE -> ASCENDING
            ASCENDING -> DESCENDING
            DESCENDING -> NONE
        }
    }
}