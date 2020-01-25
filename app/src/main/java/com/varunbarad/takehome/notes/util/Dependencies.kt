package com.varunbarad.takehome.notes.util

import android.content.Context
import androidx.room.Room
import com.varunbarad.takehome.notes.external_services.local_database.NotesDatabase
import com.varunbarad.takehome.notes.repositories.RoomNotesRepository

object Dependencies {
    private lateinit var notesDatabase: NotesDatabase

    fun getNotesDatabase(context: Context): NotesDatabase {
        synchronized(this) {
            if (!this::notesDatabase.isInitialized) {
                this.notesDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    NotesDatabase.databaseName
                ).build()
            }
        }

        return this.notesDatabase
    }

    fun getRoomNotesRepository(context: Context): RoomNotesRepository {
        return RoomNotesRepository(this.getNotesDatabase(context).notesDao())
    }
}
