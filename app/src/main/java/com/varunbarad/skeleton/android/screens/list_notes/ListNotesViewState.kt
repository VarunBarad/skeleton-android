package com.varunbarad.skeleton.android.screens.list_notes

import com.varunbarad.skeleton.android.model.UiNote

data class ListNotesViewState(
    val notes: List<UiNote>,
    val isNoStoredNotesMessageVisible: Boolean
)
