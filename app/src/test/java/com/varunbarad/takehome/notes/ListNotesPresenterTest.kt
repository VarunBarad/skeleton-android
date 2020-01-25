package com.varunbarad.takehome.notes

import com.varunbarad.takehome.notes.screens.list_notes.ListNotesPresenter
import com.varunbarad.takehome.notes.screens.list_notes.ListNotesView
import com.varunbarad.takehome.notes.util.Event
import com.varunbarad.takehome.notes.util.ThreadSchedulers
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ListNotesPresenterTest {
    @Mock
    private lateinit var listNotesView: ListNotesView

    private lateinit var listNotesPresenter: ListNotesPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ThreadSchedulers.enableTesting()

        this.listNotesPresenter = ListNotesPresenter(this.listNotesView)
    }

    @After
    fun tearDown() {
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testNewNoteScreenOpenWhenNewNoteButtonClick() {
        `when`(listNotesView.onButtonNewNoteClick()).thenReturn(Observable.just(Event.IGNORE))

        listNotesPresenter.onStart()

        verify(listNotesView).openNewNoteScreen()
    }

    @Test
    fun testNewNoteScreenNotOpenWhenNewNoteButtonNotClick() {
        `when`(listNotesView.onButtonNewNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView, never()).openNewNoteScreen()
    }
}
