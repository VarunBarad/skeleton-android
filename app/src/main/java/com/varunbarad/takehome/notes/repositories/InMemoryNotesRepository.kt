package com.varunbarad.takehome.notes.repositories

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.*

object InMemoryNotesRepository : NotesRepository {
    private val notes: MutableList<DbNote> = mutableListOf()

    private val notesSubject: BehaviorSubject<List<DbNote>> = BehaviorSubject.create()

    init {
        notesSubject.onNext(notes)
    }

    override fun insertNewNote(note: DbNote): Single<Long> {
        return Single.fromCallable {
            val noteId: Long = this.notes.size.toLong()
            this.notes.add(
                note.copy(
                    // Using index as temporary ID since note-deletion is not supported
                    // therefore ID clashes can't occur
                    id = noteId,
                    timestamp = Date(System.currentTimeMillis())
                )
            )
            notesSubject.onNext(this.notes.sortedByDescending { it.timestamp })
            noteId
        }
    }

    override fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>> {
        return this.notesSubject
    }

    override fun getNoteDetails(noteId: Long): Single<DbNote> {
        return Single.just(this.notes.first { it.id == noteId })
    }
}
