package com.varunbarad.skeleton.android.screens.list_notes.bookmarked

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.util.toUiNote
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class BookmarkedNotesListPresenter(
    private val view: BookmarkedNotesListView,
    private val notesRepository: NotesRepository
) {
    private val serviceDisposables = CompositeDisposable()

    fun onStart() {
        this.serviceDisposables.add(
            this.notesRepository
                .getBookmarkedNotesSortedReverseChronologically()
                .map { databaseNotes: List<DbNote> ->
                    databaseNotes
                        .filter { it.isBookmarked }
                        .map { it.toUiNote() }
                }
                .subscribeBy { notes ->
                    this.view.updateScreen(
                        BookmarkedNotesListViewState(
                            notes = notes,
                            isNoBookmarkedNotesMessageVisible = notes.isEmpty()
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
