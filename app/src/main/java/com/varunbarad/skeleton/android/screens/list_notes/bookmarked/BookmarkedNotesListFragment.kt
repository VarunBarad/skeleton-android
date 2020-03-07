package com.varunbarad.skeleton.android.screens.list_notes.bookmarked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.varunbarad.skeleton.android.databinding.FragmentBookmarkedListNotesBinding
import com.varunbarad.skeleton.android.model.UiNote
import com.varunbarad.skeleton.android.screens.list_notes.bookmarked.notes_adapter.NotesAdapter
import com.varunbarad.skeleton.android.screens.note_details.NoteDetailsActivity
import com.varunbarad.skeleton.android.util.Dependencies
import io.reactivex.Observable

class BookmarkedNotesListFragment : Fragment(), BookmarkedNotesListView {
    companion object {
        const val FRAGMENT_TAG = "BookmarkedNotesListFragment"
    }

    private lateinit var viewBinding: FragmentBookmarkedListNotesBinding

    private val notesAdapter = NotesAdapter()

    private lateinit var presenter: BookmarkedNotesListPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.viewBinding = FragmentBookmarkedListNotesBinding.inflate(inflater, container, false)

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

        this.presenter = BookmarkedNotesListPresenter(
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

    override fun updateScreen(viewState: BookmarkedNotesListViewState) {
        this.notesAdapter.submitList(viewState.notes)

        if (viewState.isNoBookmarkedNotesMessageVisible) {
            this.viewBinding.recyclerViewNotes.visibility = View.GONE
            this.viewBinding.textViewEmptyBookmarksMessage.visibility = View.VISIBLE
        } else {
            this.viewBinding.recyclerViewNotes.visibility = View.VISIBLE
            this.viewBinding.textViewEmptyBookmarksMessage.visibility = View.GONE
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
