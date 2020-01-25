@file:JvmName("Converters")

package com.varunbarad.takehome.notes.util

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import com.varunbarad.takehome.notes.model.UiNote

fun DbNote.toUiNote(): UiNote {
    return UiNote(
        noteId = this.id ?: throw IllegalArgumentException("Note to be shown has to have an ID"),
        title = this.title,
        content = this.contents,
        timestamp = this.timestamp
            ?: throw IllegalArgumentException("Note to be shown has to have a timestamp")
    )
}
