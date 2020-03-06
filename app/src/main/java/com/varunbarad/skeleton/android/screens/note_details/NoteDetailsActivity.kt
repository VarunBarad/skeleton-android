package com.varunbarad.skeleton.android.screens.note_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.varunbarad.skeleton.android.R
import com.varunbarad.skeleton.android.databinding.ActivityNoteDetailsBinding
import com.varunbarad.skeleton.android.util.Dependencies
import com.varunbarad.skeleton.android.util.Event
import io.reactivex.Observable

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
        this.viewBinding = ActivityNoteDetailsBinding.inflate(this.layoutInflater)
        this.setContentView(this.viewBinding.root)

        this.setSupportActionBar(this.viewBinding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.presenter = NoteDetailsPresenter(
            this,
            Dependencies.getRoomNotesRepository(this),
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

    override fun onButtonBookmarkNoteClick(): Observable<Event> {
        return Observable.create { emitter ->
            this.viewBinding.buttonBookmarkNote.setOnClickListener { emitter.onNext(Event.IGNORE) }
            emitter.setCancellable { this.viewBinding.buttonBookmarkNote.setOnClickListener(null) }
        }
    }

    override fun updateScreen(viewState: NoteDetailsViewState) {
        this.viewBinding.textViewTitle.text = viewState.noteTitleText
        this.viewBinding.textViewTimestamp.text = viewState.noteTimestampText
        this.viewBinding.textViewContent.text = viewState.noteContentText

        if (viewState.isStarFilled) {
            this.viewBinding.buttonBookmarkNote.setImageResource(R.drawable.ic_star_filled)
        } else {
            this.viewBinding.buttonBookmarkNote.setImageResource(R.drawable.ic_star_border)
        }

        if (viewState.isLoaderVisible) {
            this.viewBinding.containerNoteDetails.visibility = View.GONE
            this.viewBinding.progressBar.visibility = View.VISIBLE
        } else {
            this.viewBinding.containerNoteDetails.visibility = View.VISIBLE
            this.viewBinding.progressBar.visibility = View.GONE
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
