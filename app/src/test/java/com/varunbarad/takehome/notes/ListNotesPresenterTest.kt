package com.varunbarad.takehome.notes

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import com.varunbarad.takehome.notes.repositories.NotesRepository
import com.varunbarad.takehome.notes.screens.list_notes.ListNotesPresenter
import com.varunbarad.takehome.notes.screens.list_notes.ListNotesView
import com.varunbarad.takehome.notes.screens.list_notes.ListNotesViewState
import com.varunbarad.takehome.notes.util.Event
import com.varunbarad.takehome.notes.util.ThreadSchedulers
import com.varunbarad.takehome.notes.util.toUiNote
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

        verify(listNotesView).updateScreen(ListNotesViewState(emptyList()))
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

        verify(listNotesView).updateScreen(ListNotesViewState(notes.map { it.toUiNote() }))
    }

    private val notes: List<DbNote> = listOf(
        DbNote(1, "Title 1", "Content 1", Date(System.currentTimeMillis())),
        DbNote(2, "Title 2", "Content 2", Date(System.currentTimeMillis())),
        DbNote(3, "Title 3", "Content 3", Date(System.currentTimeMillis())),
        DbNote(4, "Title 4", "Content 4", Date(System.currentTimeMillis()))
    ).reversed() // We reverse these to get reverse-chronological sorting here
}
