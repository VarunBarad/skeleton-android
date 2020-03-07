package com.varunbarad.skeleton.android.screens.list_notes

import com.varunbarad.skeleton.android.util.Event
import io.reactivex.Observable

interface ListNotesView {
    fun onButtonCreateNoteClick(): Observable<Event>

    fun showMessage(messageText: String)

    fun openCreateNoteScreen()
}
