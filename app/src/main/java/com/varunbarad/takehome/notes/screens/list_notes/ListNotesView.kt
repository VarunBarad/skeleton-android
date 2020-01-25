package com.varunbarad.takehome.notes.screens.list_notes

import com.varunbarad.takehome.notes.util.Event
import io.reactivex.Observable

interface ListNotesView {
    fun onButtonCreateNoteClick(): Observable<Event>

    fun openCreateNoteScreen()
}
