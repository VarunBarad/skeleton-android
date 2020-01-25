package com.varunbarad.takehome.notes.model

import java.util.*

data class UiNote(
    val noteId: Long,
    val title: String,
    val content: String,
    val timestamp: Date
)
