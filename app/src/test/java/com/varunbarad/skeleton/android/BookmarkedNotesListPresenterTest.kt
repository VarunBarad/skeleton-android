package com.varunbarad.skeleton.android

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.screens.list_notes.bookmarked.BookmarkedNotesListPresenter
import com.varunbarad.skeleton.android.screens.list_notes.bookmarked.BookmarkedNotesListView
import com.varunbarad.skeleton.android.screens.list_notes.bookmarked.BookmarkedNotesListViewState
import com.varunbarad.skeleton.android.util.ThreadSchedulers
import com.varunbarad.skeleton.android.util.toUiNote
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class BookmarkedNotesListPresenterTest {
    @Mock
    private lateinit var bookmarkedNotesListView: BookmarkedNotesListView

    @Mock
    private lateinit var notesRepository: NotesRepository

    private lateinit var bookmarkedNotesListPresenter: BookmarkedNotesListPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ThreadSchedulers.enableTesting()

        this.bookmarkedNotesListPresenter = BookmarkedNotesListPresenter(
            this.bookmarkedNotesListView,
            this.notesRepository
        )
    }

    @After
    fun tearDown() {
        bookmarkedNotesListPresenter.onStop()
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testShowZeroItemsWhenZeroBookmarkedItemsReturnedFromRepository() {
        `when`(notesRepository.getBookmarkedNotesSortedReverseChronologically()).thenReturn(
            Observable.just(emptyList())
        )
        `when`(bookmarkedNotesListView.onNoteClick()).thenReturn(Observable.never())

        bookmarkedNotesListPresenter.onStart()

        verify(bookmarkedNotesListView).updateScreen(
            BookmarkedNotesListViewState(emptyList(), true)
        )
    }

    @Test
    fun testShow5ItemsWhen5ItemsReturnedFromRepository() {
        `when`(notesRepository.getBookmarkedNotesSortedReverseChronologically()).thenReturn(
            Observable.just(bookmarkedNotes)
        )
        `when`(bookmarkedNotesListView.onNoteClick()).thenReturn(Observable.never())

        bookmarkedNotesListPresenter.onStart()

        verify(bookmarkedNotesListView).updateScreen(
            BookmarkedNotesListViewState(
                bookmarkedNotes.map { it.toUiNote() },
                false
            )
        )
    }

    @Test
    fun testShowNoBookmarkedNotesMessageWhenZeroBookmarkedNotesReturnedFromRepository() {
        `when`(notesRepository.getBookmarkedNotesSortedReverseChronologically()).thenReturn(
            Observable.just(emptyList())
        )
        `when`(bookmarkedNotesListView.onNoteClick()).thenReturn(Observable.never())

        bookmarkedNotesListPresenter.onStart()

        verify(bookmarkedNotesListView).updateScreen(
            BookmarkedNotesListViewState(
                emptyList(),
                true
            )
        )
    }

    @Test
    fun testNoShowNoBookmarkedNotesMessageWhen5BookmarkedNotesReturnedFromRepository() {
        `when`(notesRepository.getBookmarkedNotesSortedReverseChronologically()).thenReturn(
            Observable.just(bookmarkedNotes)
        )
        `when`(bookmarkedNotesListView.onNoteClick()).thenReturn(Observable.never())

        bookmarkedNotesListPresenter.onStart()

        verify(bookmarkedNotesListView).updateScreen(
            BookmarkedNotesListViewState(
                bookmarkedNotes.map { it.toUiNote() },
                false
            )
        )
    }

    @Test
    fun testOpenNoteDetailsScreenCalledWithCorrectNoteIdWhenNoteIsClicked() {
        val note = bookmarkedNotes.first().toUiNote()

        `when`(notesRepository.getBookmarkedNotesSortedReverseChronologically()).thenReturn(
            Observable.never()
        )
        `when`(bookmarkedNotesListView.onNoteClick()).thenReturn(Observable.just(note))

        bookmarkedNotesListPresenter.onStart()

        verify(bookmarkedNotesListView).openNoteDetailsScreen(note.noteId)
    }

    @Test
    fun testOpenNoteDetailsScreenNotCalledWhenNoNoteIsClicked() {
        `when`(notesRepository.getBookmarkedNotesSortedReverseChronologically()).thenReturn(
            Observable.never()
        )
        `when`(bookmarkedNotesListView.onNoteClick()).thenReturn(Observable.never())

        bookmarkedNotesListPresenter.onStart()

        verify(bookmarkedNotesListView, never()).openNoteDetailsScreen(anyLong())
    }

    private val allNotes: List<DbNote> = listOf(
        DbNote(1, "Title 1", "Content 1", Date(System.currentTimeMillis()), true),
        DbNote(2, "Title 2", "Content 2", Date(System.currentTimeMillis()), false),
        DbNote(3, "Title 3", "Content 3", Date(System.currentTimeMillis()), false),
        DbNote(4, "Title 4", "Content 4", Date(System.currentTimeMillis()), true),
        DbNote(5, "Title 5", "Content 5", Date(System.currentTimeMillis()), true),
        DbNote(6, "Title 6", "Content 6", Date(System.currentTimeMillis()), true),
        DbNote(7, "Title 7", "Content 7", Date(System.currentTimeMillis()), false),
        DbNote(8, "Title81", "Cont8nt 1", Date(System.currentTimeMillis()), true),
        DbNote(9, "Title91", "Cont9nt 1", Date(System.currentTimeMillis()), false)
    ).reversed() // We reverse these to get reverse-chronological sorting here

    private val bookmarkedNotes: List<DbNote> = allNotes.filter { it.isBookmarked }
}
