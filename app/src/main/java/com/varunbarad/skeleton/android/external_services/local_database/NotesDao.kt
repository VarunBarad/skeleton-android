package com.varunbarad.skeleton.android.external_services.local_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface NotesDao {
    @Insert
    fun insertNote(note: DbNote): Single<Long>

    @Query("select * from Notes order by timestamp desc")
    fun getAllNotesSortedReverseChronologically(): Observable<List<DbNote>>

    @Query("select * from Notes where isBookmarked = 1 order by timestamp desc")
    fun getBookmarkedNotesSortedReverseChronologically(): Observable<List<DbNote>>

    @Query("select * from Notes where id = :noteId")
    fun getNoteDetails(noteId: Long): Observable<DbNote>

    @Update
    fun updateNoteDetails(note: DbNote): Completable
}
