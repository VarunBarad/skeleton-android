package com.varunbarad.takehome.notes.screens.list_notes

import com.varunbarad.takehome.notes.model.UiNote
import com.varunbarad.takehome.notes.util.Event
import io.reactivex.Observable

interface ListNotesView {
    fun onButtonCreateNoteClick(): Observable<Event>

    fun onNoteClick(): Observable<UiNote>

    fun openCreateNoteScreen()
    fun openNoteDetailsScreen(noteId: Long)
}
