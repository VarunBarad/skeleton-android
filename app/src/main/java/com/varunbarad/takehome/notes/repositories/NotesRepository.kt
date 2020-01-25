package com.varunbarad.takehome.notes.repositories

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import io.reactivex.Observable
import io.reactivex.Single

interface NotesRepository {
    fun insertNewNote(note: DbNote): Single<Long>

    fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>>

    fun getNoteDetails(noteId: Long): Single<DbNote>
}
