package com.varunbarad.takehome.notes.screens.list_notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.varunbarad.takehome.notes.R
import com.varunbarad.takehome.notes.databinding.ActivityListNotesBinding
import com.varunbarad.takehome.notes.screens.create_note.CreateNoteActivity
import com.varunbarad.takehome.notes.util.Event
import io.reactivex.Observable

class ListNotesActivity : AppCompatActivity(), ListNotesView {
    private lateinit var viewBinding: ActivityListNotesBinding

    private lateinit var presenter: ListNotesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_notes)
        this.setSupportActionBar(this.viewBinding.toolbar)

        this.presenter = ListNotesPresenter(this)
    }

    override fun onStart() {
        super.onStart()

        this.presenter.onStart()
    }

    override fun onStop() {
        this.presenter.onStop()

        this.viewBinding.buttonCreateNote.setOnClickListener(null)

        super.onStop()
    }

    override fun onButtonCreateNoteClick(): Observable<Event> {
        return Observable.create { emitter ->
            this.viewBinding.buttonCreateNote.setOnClickListener { emitter.onNext(Event.IGNORE) }
            emitter.setCancellable { this.viewBinding.buttonCreateNote.setOnClickListener(null) }
        }
    }

    override fun openCreateNoteScreen() {
        CreateNoteActivity.start(this)
    }
}
