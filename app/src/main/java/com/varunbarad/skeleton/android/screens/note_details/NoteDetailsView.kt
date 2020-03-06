package com.varunbarad.skeleton.android.screens.note_details

import com.varunbarad.skeleton.android.util.Event
import io.reactivex.Observable

interface NoteDetailsView {
    fun onButtonBookmarkNoteClick(): Observable<Event>

    fun updateScreen(viewState: NoteDetailsViewState)

    fun showMessage(messageText: String)
}
