package com.varunbarad.takehome.notes.screens.create_note

import com.varunbarad.takehome.notes.repositories.NotesRepository
import io.reactivex.disposables.CompositeDisposable

class CreateNotePresenter(
    private val view: CreateNoteView,
    private val notesRepository: NotesRepository
) {
    private val serviceDisposables = CompositeDisposable()

    fun onStart() {

    }

    fun onStop() {
        this.serviceDisposables.clear()
    }
}
