package com.varunbarad.skeleton.android

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.screens.list_notes.ListNotesPresenter
import com.varunbarad.skeleton.android.screens.list_notes.ListNotesView
import com.varunbarad.skeleton.android.screens.list_notes.ListNotesViewState
import com.varunbarad.skeleton.android.util.Event
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

class ListNotesPresenterTest {
    @Mock
    private lateinit var listNotesView: ListNotesView
    @Mock
    private lateinit var notesRepository: NotesRepository

    private lateinit var listNotesPresenter: ListNotesPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ThreadSchedulers.enableTesting()

        this.listNotesPresenter = ListNotesPresenter(
            this.listNotesView,
            this.notesRepository
        )
    }

    @After
    fun tearDown() {
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testNewNoteScreenOpenWhenNewNoteButtonClick() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(Observable.never())
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView).openCreateNoteScreen()
    }

    @Test
    fun testNewNoteScreenNotOpenWhenNewNoteButtonNotClick() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(Observable.never())
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView, never()).openCreateNoteScreen()
    }

    @Test
    fun testShowZeroItemsWhenZeroItemsReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(
                emptyList()
            )
        )
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView).updateScreen(ListNotesViewState(emptyList(), true))
    }

    @Test
    fun testShow4ItemsWhen4ItemsReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(
                notes
            )
        )
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView).updateScreen(ListNotesViewState(notes.map { it.toUiNote() }, false))
    }

    @Test
    fun testShowNoStoredNotesMessageWhenZeroItemsReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(
                emptyList()
            )
        )
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView).updateScreen(ListNotesViewState(emptyList(), true))
    }

    @Test
    fun testNoShowNoStoredNotesMessageWhen4ItemsReturnedFromRepository() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(
            Observable.just(
                notes
            )
        )
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView).updateScreen(ListNotesViewState(notes.map { it.toUiNote() }, false))
    }

    @Test
    fun testOpenNoteDetailsScreenCalledWithCorrectNoteIdWhenNoteIsClicked() {
        val note = notes[0].toUiNote()

        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(Observable.never())
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.just(note))

        listNotesPresenter.onStart()

        verify(listNotesView).openNoteDetailsScreen(note.noteId)
    }

    @Test
    fun testOpenNoteDetailsScreenNotCalledWhenNoNoteIsClicked() {
        `when`(notesRepository.getAllNotesSortedReverseChronologically()).thenReturn(Observable.never())
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())
        `when`(listNotesView.onNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView, never()).openNoteDetailsScreen(anyLong())
    }

    private val notes: List<DbNote> = listOf(
        DbNote(1, "Title 1", "Content 1", Date(System.currentTimeMillis())),
        DbNote(2, "Title 2", "Content 2", Date(System.currentTimeMillis())),
        DbNote(3, "Title 3", "Content 3", Date(System.currentTimeMillis())),
        DbNote(4, "Title 4", "Content 4", Date(System.currentTimeMillis()))
    ).reversed() // We reverse these to get reverse-chronological sorting here
}
