package com.varunbarad.takehome.notes.screens.note_details

import com.varunbarad.takehome.notes.repositories.NotesRepository
import io.reactivex.disposables.CompositeDisposable

class NoteDetailsPresenter(
    private val view: NoteDetailsView,
    private val notesRepository: NotesRepository,
    private val noteId: Long
) {
    private val serviceDisposables = CompositeDisposable()

    fun onStart() {

    }

    fun onStop() {
        this.serviceDisposables.clear()
    }
}
