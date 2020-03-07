package com.varunbarad.skeleton.android.screens.list_notes.all

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.util.toUiNote
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class AllNotesListPresenter(
    private val view: AllNotesListView,
    private val notesRepository: NotesRepository
) {
    private val serviceDisposables = CompositeDisposable()

    fun onStart() {
        this.serviceDisposables.add(
            this.notesRepository
                .getAllNotesSortedReverseChronologically()
                .map { databaseNotes: List<DbNote> -> databaseNotes.map { it.toUiNote() } }
                .subscribeBy { notes ->
                    this.view.updateScreen(
                        AllNotesListViewState(
                            notes = notes,
                            isNoStoredNotesMessageVisible = notes.isEmpty()
                        )
                    )
                }
        )

        this.serviceDisposables.add(
            this.view
                .onNoteClick()
                .subscribeBy { note -> this.view.openNoteDetailsScreen(note.noteId) }
        )
    }

    fun onStop() {
        this.serviceDisposables.clear()
    }
}
