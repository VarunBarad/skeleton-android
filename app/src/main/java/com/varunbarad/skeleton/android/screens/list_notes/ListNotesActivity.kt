package com.varunbarad.skeleton.android.screens.list_notes

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.varunbarad.skeleton.android.R
import com.varunbarad.skeleton.android.databinding.ActivityListNotesBinding
import com.varunbarad.skeleton.android.screens.create_note.CreateNoteActivity
import com.varunbarad.skeleton.android.screens.list_notes.all.AllNotesListFragment
import com.varunbarad.skeleton.android.screens.list_notes.bookmarked.BookmarkedNotesListFragment
import com.varunbarad.skeleton.android.util.Event
import io.reactivex.Observable

class ListNotesActivity : AppCompatActivity(), ListNotesView {
    private lateinit var viewBinding: ActivityListNotesBinding

    private lateinit var presenter: ListNotesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = ActivityListNotesBinding.inflate(this.layoutInflater)
        this.setContentView(this.viewBinding.root)
        this.setSupportActionBar(this.viewBinding.toolbar)

        if (savedInstanceState == null) {
            val initialFragment = this.supportFragmentManager
                .findFragmentByTag(AllNotesListFragment.FRAGMENT_TAG)
                ?: AllNotesListFragment()

            this.supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container_fragment,
                    initialFragment,
                    AllNotesListFragment.FRAGMENT_TAG
                ).commitAllowingStateLoss()
        }

        this.presenter = ListNotesPresenter(this)
    }

    override fun onStart() {
        super.onStart()

        this.viewBinding
            .bottomNavigationView
            .setOnNavigationItemSelectedListener(this::onBottomNavigationItemSelected)

        this.presenter.onStart()
    }

    override fun onStop() {
        this.presenter.onStop()

        this.viewBinding.buttonCreateNote.setOnClickListener(null)
        this.viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener(null)

        super.onStop()
    }

    private fun onBottomNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.category_all -> {
                val allNotesListFragment = this.supportFragmentManager
                    .findFragmentByTag(AllNotesListFragment.FRAGMENT_TAG)
                    ?: AllNotesListFragment()

                this.supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        allNotesListFragment,
                        AllNotesListFragment.FRAGMENT_TAG
                    ).commitAllowingStateLoss()

                return true
            }
            R.id.category_bookmarked -> {
                val bookmarkedNotesListFragment = this.supportFragmentManager
                    .findFragmentByTag(BookmarkedNotesListFragment.FRAGMENT_TAG)
                    ?: BookmarkedNotesListFragment()

                this.supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container_fragment,
                        bookmarkedNotesListFragment,
                        BookmarkedNotesListFragment.FRAGMENT_TAG
                    ).commitAllowingStateLoss()

                return true
            }
        }

        return false
    }

    override fun onButtonCreateNoteClick(): Observable<Event> {
        return Observable.create { emitter ->
            this.viewBinding.buttonCreateNote.setOnClickListener { emitter.onNext(Event.IGNORE) }
            emitter.setCancellable { this.viewBinding.buttonCreateNote.setOnClickListener(null) }
        }
    }

    override fun showMessage(messageText: String) {
        Snackbar.make(
            this.viewBinding.root,
            messageText,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun openCreateNoteScreen() {
        CreateNoteActivity.start(this)
    }
}
