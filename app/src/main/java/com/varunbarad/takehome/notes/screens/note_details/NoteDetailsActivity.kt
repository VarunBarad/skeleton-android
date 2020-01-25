package com.varunbarad.takehome.notes.screens.note_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.varunbarad.takehome.notes.R
import com.varunbarad.takehome.notes.databinding.ActivityNoteDetailsBinding
import com.varunbarad.takehome.notes.repositories.InMemoryNotesRepository

class NoteDetailsActivity : AppCompatActivity(), NoteDetailsView {
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

    private lateinit var presenter: NoteDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_note_details
        )

        this.setSupportActionBar(this.viewBinding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.presenter = NoteDetailsPresenter(
            this,
            InMemoryNotesRepository,
            this.getPassedNoteId()
        )
    }

    override fun onStart() {
        super.onStart()

        this.presenter.onStart()
    }

    override fun onStop() {
        this.presenter.onStop()

        super.onStop()
    }

    private fun getPassedNoteId(): Long {
        val passedNoteId = this.intent.extras?.getLong(EXTRA_NOTE_ID)

        if (passedNoteId == null) {
            throw IllegalArgumentException("No note-id was passed to note-details screen")
        } else {
            return passedNoteId
        }
    }

    override fun updateScreen(viewState: NoteDetailsViewState) {
        this.viewBinding.textViewTitle.text = viewState.noteTitleText
        this.viewBinding.textViewTimestamp.text = viewState.noteTimestampText
        this.viewBinding.textViewContent.text = viewState.noteContentText

        if (viewState.isLoaderVisible) {
            // ToDo: Hide all text fields and show loader
        } else {
            // ToDo: Hide the loader and show all 3 text fields
        }
    }

    override fun showMessage(messageText: String) {
        Snackbar.make(
            this.viewBinding.root,
            messageText,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
