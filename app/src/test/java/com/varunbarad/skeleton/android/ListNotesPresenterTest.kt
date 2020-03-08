package com.varunbarad.skeleton.android

import com.varunbarad.skeleton.android.screens.list_notes.ListNotesPresenter
import com.varunbarad.skeleton.android.screens.list_notes.ListNotesView
import com.varunbarad.skeleton.android.util.Event
import com.varunbarad.skeleton.android.util.ThreadSchedulers
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
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.just(Event.IGNORE))

        listNotesPresenter.onStart()

        verify(listNotesView).openCreateNoteScreen()
    }

    @Test
    fun testNewNoteScreenNotOpenWhenNewNoteButtonNotClick() {
        `when`(listNotesView.onButtonCreateNoteClick()).thenReturn(Observable.never())

        listNotesPresenter.onStart()

        verify(listNotesView, never()).openCreateNoteScreen()
    }
}
