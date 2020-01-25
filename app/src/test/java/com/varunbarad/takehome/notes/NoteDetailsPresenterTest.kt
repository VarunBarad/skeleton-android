package com.varunbarad.takehome.notes

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import com.varunbarad.takehome.notes.repositories.NotesRepository
import com.varunbarad.takehome.notes.screens.note_details.NoteDetailsPresenter
import com.varunbarad.takehome.notes.screens.note_details.NoteDetailsView
import com.varunbarad.takehome.notes.screens.note_details.NoteDetailsViewState
import com.varunbarad.takehome.notes.util.ThreadSchedulers
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailsPresenterTest {
    @Mock
    private lateinit var noteDetailsView: NoteDetailsView
    @Mock
    private lateinit var notesRepository: NotesRepository

    private lateinit var noteDetailsPresenter: NoteDetailsPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ThreadSchedulers.enableTesting()

        this.noteDetailsPresenter = NoteDetailsPresenter(
            this.noteDetailsView,
            this.notesRepository,
            noteId
        )
    }

    @After
    fun tearDown() {
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testLoaderShownFirstWhenScreenStarts() {
        `when`(notesRepository.getNoteDetails(noteId)).thenReturn(Single.just(note))

        noteDetailsPresenter.onStart()

        verify(noteDetailsView).updateScreen(
            NoteDetailsViewState(
                noteTitleText = "",
                noteContentText = "",
                noteTimestampText = "",
                isLoaderVisible = true
            )
        )
    }

    @Test
    fun testNoteDetailsShownAfterLoader() {
        `when`(notesRepository.getNoteDetails(noteId)).thenReturn(Single.just(note))

        noteDetailsPresenter.onStart()

        verify(noteDetailsView).updateScreen(
            NoteDetailsViewState(
                noteTitleText = "",
                noteContentText = "",
                noteTimestampText = "",
                isLoaderVisible = true
            )
        )
        verify(noteDetailsView).updateScreen(
            NoteDetailsViewState(
                noteTitleText = note.title,
                noteContentText = note.contents,
                noteTimestampText = formattedTimestampText,
                isLoaderVisible = false
            )
        )
    }

    private val noteId: Long = 1
    private val note: DbNote = DbNote(
        id = noteId,
        title = "Note title",
        contents = "Note contents go here",
        timestamp = Date(System.currentTimeMillis())
    )
    private val formattedTimestampText: String = SimpleDateFormat(
        "dd LLL yyyy, hh:mm a",
        Locale.getDefault()
    ).format(note.timestamp!!) // We know that timestamp is not null here since we initialized it just above
}
