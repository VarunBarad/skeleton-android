package com.varunbarad.skeleton.android

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.screens.list_notes.all.AllNotesListPresenter
import com.varunbarad.skeleton.android.screens.list_notes.all.AllNotesListView
import com.varunbarad.skeleton.android.screens.list_notes.all.AllNotesListViewState
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

class AllNotesListPresenterTest {
    @Mock
    private lateinit var allNotesListView: AllNotesListView

    @Mock
    private lateinit var notesRepository: NotesRepository

    private lateinit var allNotesListPresenter: AllNotesListPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ThreadSchedulers.enableTesting()

        this.allNotesListPresenter = AllNotesListPresenter(
            this.allNotesListView,
            this.notesRepository
        )
    }

    @After
    fun tearDown() {
        this.allNotesListPresenter.onStop()
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testShowZeroItemsWhenZeroNotesReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(emptyList())
        )
        `when`(allNotesListView.onNoteClick()).thenReturn(Observable.never())

        allNotesListPresenter.onStart()

        verify(allNotesListView).updateScreen(AllNotesListViewState(emptyList(), true))
    }

    @Test
    fun testShow9NotesWhen9NotesReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(allNotes)
        )
        `when`(allNotesListView.onNoteClick()).thenReturn(Observable.never())

        allNotesListPresenter.onStart()

        verify(allNotesListView).updateScreen(
            AllNotesListViewState(
                allNotes.map { it.toUiNote() },
                false
            )
        )
    }

    @Test
    fun testShowNoSavedNotesMessageWhenZeroNotesReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(emptyList())
        )
        `when`(allNotesListView.onNoteClick()).thenReturn(Observable.never())

        allNotesListPresenter.onStart()

        verify(allNotesListView).updateScreen(
            AllNotesListViewState(
                emptyList(),
                true
            )
        )
    }

    @Test
    fun testNoShowNoSavedNotesMessageWhen9SavedNotesReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(allNotes)
        )
        `when`(allNotesListView.onNoteClick()).thenReturn(Observable.never())

        allNotesListPresenter.onStart()

        verify(allNotesListView).updateScreen(
            AllNotesListViewState(
                allNotes.map { it.toUiNote() },
                false
            )
        )
    }

    @Test
    fun testOpenNoteDetailsScreenCalledWithCorrectNoteIdWhenNoteIsClicked() {
        val note = allNotes.first().toUiNote()

        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.never()
        )
        `when`(allNotesListView.onNoteClick()).thenReturn(Observable.just(note))

        allNotesListPresenter.onStart()

        verify(allNotesListView).openNoteDetailsScreen(note.noteId)
    }

    @Test
    fun testOpenNoteDetailsScreenNotCalledWhenNoNoteIsClicked() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.never()
        )
        `when`(allNotesListView.onNoteClick()).thenReturn(Observable.never())

        allNotesListPresenter.onStart()

        verify(allNotesListView, never()).openNoteDetailsScreen(anyLong())
    }

    private val allNotes: List<DbNote> = listOf(
        DbNote(1, "Title 1", "Content 1", Date(System.currentTimeMillis()), true),
        DbNote(2, "Title 2", "Content 2", Date(System.currentTimeMillis()), false),
        DbNote(3, "Title 3", "Content 3", Date(System.currentTimeMillis()), false),
        DbNote(4, "Title 4", "Content 4", Date(System.currentTimeMillis()), true),
        DbNote(5, "Title 5", "Content 5", Date(System.currentTimeMillis()), true),
        DbNote(6, "Title 6", "Content 6", Date(System.currentTimeMillis()), true),
        DbNote(7, "Title 7", "Content 7", Date(System.currentTimeMillis()), false),
        DbNote(8, "Title 8", "Content 8", Date(System.currentTimeMillis()), true),
        DbNote(9, "Title 9", "Content 9", Date(System.currentTimeMillis()), false)
    ).reversed() // We reverse these to get reverse-chronological sorting here
}
