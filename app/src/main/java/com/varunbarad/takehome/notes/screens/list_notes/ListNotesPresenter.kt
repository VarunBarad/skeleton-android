package com.varunbarad.takehome.notes.screens.list_notes

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ListNotesPresenter(
    private val view: ListNotesView
) {
    private val serviceDisposables = CompositeDisposable()

    fun onStart() {
        this.serviceDisposables.add(
            this.view
                .onButtonNewNoteClick()
                .subscribeBy {
                    this.view.openNewNoteScreen()
                }
        )
    }

    fun onStop() {
        this.serviceDisposables.clear()
    }
}
