package com.varunbarad.skeleton.android.repositories

import com.varunbarad.skeleton.android.external_services.local_database.NotesDao
import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.util.ThreadSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

class RoomNotesRepository(private val notesDao: NotesDao) : NotesRepository {
    override fun insertNewNote(note: DbNote): Single<Long> {
        return notesDao.insertNote(
            note.copy(timestamp = Date(System.currentTimeMillis()))
        ).subscribeOn(ThreadSchedulers.io())
            .observeOn(ThreadSchedulers.main())
    }

    override fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>> {
        return notesDao.getAllNotesSortedReverseChronologically()
            .subscribeOn(ThreadSchedulers.io())
            .observeOn(ThreadSchedulers.main())
    }

    override fun getBookmarkedNotesSortedReverseChronologically(): Observable<List<DbNote>> {
        return notesDao.getBookmarkedNotesSortedReverseChronologically()
            .subscribeOn(ThreadSchedulers.io())
            .observeOn(ThreadSchedulers.main())
    }

    override fun getNoteDetails(noteId: Long): Observable<DbNote> {
        return notesDao.getNoteDetails(noteId)
            .subscribeOn(ThreadSchedulers.io())
            .observeOn(ThreadSchedulers.main())
    }

    override fun updateNote(note: DbNote): Completable {
        return notesDao.updateNoteDetails(note)
            .subscribeOn(ThreadSchedulers.io())
            .observeOn(ThreadSchedulers.main())
    }
}
