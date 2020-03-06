package com.varunbarad.skeleton.android.screens.note_details

import android.util.Log
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.util.toUiNote
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailsPresenter(
    private val view: NoteDetailsView,
    private val notesRepository: NotesRepository,
    private val noteId: Long
) {
    private val timestampFormat = SimpleDateFormat("dd LLL yyyy, hh:mm a", Locale.getDefault())

    private val serviceDisposables = CompositeDisposable()

    fun onStart() {
        this.serviceDisposables.add(
            this.notesRepository
                .getNoteDetails(noteId)
                .map { dbNote -> dbNote.toUiNote() }
                .subscribeBy(
                    onError = { error ->
                        this.view.updateScreen(
                            NoteDetailsViewState(
                                noteTitleText = "",
                                noteContentText = "",
                                noteTimestampText = "",
                                isStarFilled = false,
                                isLoaderVisible = false
                            )
                        )
                        Log.e(
                            NoteDetailsPresenter::class.java.canonicalName,
                            error.message ?: "Error message was null"
                        )
                        this.view.showMessage("Error loading note. Try again later.")
                    },
                    onNext = { note ->
                        this.view.updateScreen(
                            NoteDetailsViewState(
                                noteTitleText = note.title,
                                noteContentText = note.content,
                                noteTimestampText = this.timestampFormat.format(note.timestamp),
                                isStarFilled = note.isBookmarked,
                                isLoaderVisible = false
                            )
                        )
                    }
                )
        )

        this.serviceDisposables.add(
            this.view
                .onButtonBookmarkNoteClick()
                .flatMapSingle { this.notesRepository.getNoteDetails(this.noteId).firstOrError() }
                .flatMapSingle { note ->
                    val newBookmarkState = !note.isBookmarked
                    this.notesRepository
                        .updateNote(note.copy(isBookmarked = newBookmarkState))
                        .toSingle { newBookmarkState }
                }.subscribeBy { isBookmarked ->
                    val message = if (isBookmarked) {
                        "Note bookmarked"
                    } else {
                        "Bookmark removed from note"
                    }
                    this.view.showMessage(message)
                }
        )

        this.view.updateScreen(
            NoteDetailsViewState(
                noteTitleText = "",
                noteContentText = "",
                noteTimestampText = "",
                isStarFilled = false,
                isLoaderVisible = true
            )
        )
    }

    fun onStop() {
        this.serviceDisposables.clear()
    }
}
