package com.varunbarad.skeleton.android.screens.list_notes.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.varunbarad.skeleton.android.databinding.FragmentAllListNotesBinding
import com.varunbarad.skeleton.android.model.UiNote
import com.varunbarad.skeleton.android.screens.list_notes.all.notes_adapter.NotesAdapter
import com.varunbarad.skeleton.android.screens.note_details.NoteDetailsActivity
import com.varunbarad.skeleton.android.util.Dependencies
import io.reactivex.Observable

class AllNotesListFragment : Fragment(), AllNotesListView {
    companion object {
        const val FRAGMENT_TAG = "AllNotesListFragment"
    }

    private lateinit var viewBinding: FragmentAllListNotesBinding

    private val notesAdapter = NotesAdapter()

    private lateinit var presenter: AllNotesListPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.viewBinding = FragmentAllListNotesBinding.inflate(inflater, container, false)

        val recyclerViewLayoutManager = LinearLayoutManager(
            this.viewBinding.recyclerViewNotes.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        this.viewBinding.recyclerViewNotes.layoutManager = recyclerViewLayoutManager
        this.viewBinding.recyclerViewNotes.addItemDecoration(
            DividerItemDecoration(
                this.viewBinding.recyclerViewNotes.context,
                recyclerViewLayoutManager.orientation
            )
        )
        this.viewBinding.recyclerViewNotes.adapter = this.notesAdapter

        this.presenter = AllNotesListPresenter(
            this,
            Dependencies.getRoomNotesRepository(this.requireContext())
        )

        return this.viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        this.presenter.onStart()
    }

    override fun onStop() {
        this.presenter.onStop()

        super.onStop()
    }

    override fun onNoteClick(): Observable<UiNote> = this.notesAdapter.getNoteClickObservable()

    override fun updateScreen(viewState: AllNotesListViewState) {
        this.notesAdapter.submitList(viewState.notes)

        if (viewState.isNoStoredNotesMessageVisible) {
            this.viewBinding.recyclerViewNotes.visibility = View.GONE
            this.viewBinding.textViewEmptyNotesMessage.visibility = View.VISIBLE
        } else {
            this.viewBinding.recyclerViewNotes.visibility = View.VISIBLE
            this.viewBinding.textViewEmptyNotesMessage.visibility = View.GONE
        }
    }

    override fun showMessage(messageText: String) {
        Snackbar.make(
            this.viewBinding.root,
            messageText,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun openNoteDetailsScreen(noteId: Long) {
        NoteDetailsActivity.start(this.requireContext(), noteId)
    }
}
