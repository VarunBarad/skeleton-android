package com.varunbarad.skeleton.android.repositories

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NotesRepository {
    fun insertNewNote(note: DbNote): Single<Long>

    fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>>
    fun getBookmarkedNotesSortedReverseChronologically(): Observable<List<DbNote>>

    fun getNoteDetails(noteId: Long): Single<DbNote>

    fun updateNote(note: DbNote): Completable
}
