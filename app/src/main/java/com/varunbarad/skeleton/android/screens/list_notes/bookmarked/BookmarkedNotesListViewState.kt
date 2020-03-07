package com.varunbarad.skeleton.android.screens.list_notes.bookmarked

import com.varunbarad.skeleton.android.model.UiNote

data class BookmarkedNotesListViewState(
    val notes: List<UiNote>,
    val isNoBookmarkedNotesMessageVisible: Boolean
)
