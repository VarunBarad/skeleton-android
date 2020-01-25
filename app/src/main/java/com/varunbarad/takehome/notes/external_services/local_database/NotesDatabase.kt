package com.varunbarad.takehome.notes.external_services.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote

@Database(
    entities = [
        DbNote::class
    ],
    version = NotesDatabase.databaseVersion
)
@TypeConverters(RoomTypeConverters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        const val databaseVersion = 1
        const val databaseName = "TakeHome-NotesDatabase"
    }
}
