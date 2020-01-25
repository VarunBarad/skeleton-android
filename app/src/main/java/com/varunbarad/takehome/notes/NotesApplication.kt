package com.varunbarad.takehome.notes

import android.app.Application
import com.varunbarad.takehome.notes.util.Dependencies

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // This initializes the notes database
        Dependencies.getNotesDatabase(this)
    }
}
