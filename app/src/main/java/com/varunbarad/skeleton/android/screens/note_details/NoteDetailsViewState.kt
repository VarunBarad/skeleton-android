package com.varunbarad.skeleton.android.screens.note_details

data class NoteDetailsViewState(
    val noteTitleText: String,
    val noteTimestampText: String,
    val noteContentText: String,
    val isStarFilled: Boolean,
    val isLoaderVisible: Boolean
)
