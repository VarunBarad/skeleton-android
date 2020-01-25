package com.varunbarad.takehome.notes.screens.list_notes

import com.varunbarad.takehome.notes.repositories.NotesRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ListNotesPresenter(
    private val view: ListNotesView,
    private val notesRepository: NotesRepository
) {
    private val serviceDisposables = CompositeDisposable()

    fun onStart() {
        this.serviceDisposables.add(
            this.view
                .onButtonCreateNoteClick()
                .subscribeBy {
                    this.view.openCreateNoteScreen()
                }
        )
    }

    fun onStop() {
        this.serviceDisposables.clear()
    }
}
