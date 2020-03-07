package com.varunbarad.skeleton.android.screens.list_notes.all

import com.varunbarad.skeleton.android.model.UiNote

data class AllNotesListViewState(
    val notes: List<UiNote>,
    val isNoStoredNotesMessageVisible: Boolean
)
