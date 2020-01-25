package com.varunbarad.takehome.notes.repositories

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class InMemoryNotesRepository : NotesRepository {
    private val notes: MutableList<DbNote> = mutableListOf()

    private val notesSubject: BehaviorSubject<List<DbNote>> = BehaviorSubject.create()

    init {
        notesSubject.onNext(notes)
    }

    override fun insertNewNote(note: DbNote): Completable {
        return Completable.fromCallable {
            this.notes.add(
                note.copy(
                    // Using index as temporary ID since note-deletion is not supported
                    // therefore ID clashes can't occur
                    id = this.notes.size,
                    timestamp = Date(System.currentTimeMillis())
                )
            )
        }.andThen {
            notesSubject.onNext(this.notes.sortedByDescending { it.timestamp })
        }
    }

    override fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>> {
        return this.notesSubject
    }
}
