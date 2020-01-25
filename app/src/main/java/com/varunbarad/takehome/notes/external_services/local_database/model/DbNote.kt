package com.varunbarad.takehome.notes.external_services.local_database.model

import java.util.*

data class DbNote(
    val id: Long? = null,
    val title: String,
    val contents: String,
    val timestamp: Date? = null
)
