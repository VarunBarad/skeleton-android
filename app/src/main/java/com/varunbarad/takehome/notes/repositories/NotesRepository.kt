package com.varunbarad.takehome.notes.repositories

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import io.reactivex.Completable
import io.reactivex.Observable

interface NotesRepository {
    fun insertNewNote(note: DbNote): Completable

    fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>>
}
