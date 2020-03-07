package com.varunbarad.skeleton.android.screens.list_notes

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ListNotesPresenter(
    private val view: ListNotesView
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
