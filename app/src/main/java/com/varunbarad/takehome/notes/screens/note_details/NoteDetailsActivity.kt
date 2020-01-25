package com.varunbarad.takehome.notes.screens.note_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.varunbarad.takehome.notes.R
import com.varunbarad.takehome.notes.databinding.ActivityNoteDetailsBinding

class NoteDetailsActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_NOTE_ID = "note-id"

        @JvmStatic
        fun start(context: Context, noteId: Long) {
            context.startActivity(Intent(context, NoteDetailsActivity::class.java).apply {
                putExtra(EXTRA_NOTE_ID, noteId)
            })
        }
    }

    private lateinit var viewBinding: ActivityNoteDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_note_details
        )

        this.setSupportActionBar(this.viewBinding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
