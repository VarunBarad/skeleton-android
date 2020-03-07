package com.varunbarad.skeleton.android.screens.list_notes.bookmarked

import com.varunbarad.skeleton.android.model.UiNote
import io.reactivex.Observable

interface BookmarkedNotesListView {
    fun onNoteClick(): Observable<UiNote>

    fun updateScreen(viewState: BookmarkedNotesListViewState)
    fun showMessage(messageText: String)

    fun openNoteDetailsScreen(noteId: Long)
}
