package com.varunbarad.skeleton.android

import android.app.Application
import com.varunbarad.skeleton.android.util.Dependencies

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // This initializes the notes database
        Dependencies.getNotesDatabase(this)
    }
}
