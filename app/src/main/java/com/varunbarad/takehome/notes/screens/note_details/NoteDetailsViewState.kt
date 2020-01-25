package com.varunbarad.takehome.notes.screens.note_details

data class NoteDetailsViewState(
    val noteTitleText: String,
    val noteTimestampText: String,
    val noteContentText: String,
    val isLoaderVisible: Boolean
)
