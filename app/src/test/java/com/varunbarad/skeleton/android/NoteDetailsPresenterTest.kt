package com.varunbarad.skeleton.android

import com.varunbarad.skeleton.android.external_services.local_database.model.DbNote
import com.varunbarad.skeleton.android.repositories.NotesRepository
import com.varunbarad.skeleton.android.screens.note_details.NoteDetailsPresenter
import com.varunbarad.skeleton.android.screens.note_details.NoteDetailsView
import com.varunbarad.skeleton.android.screens.note_details.NoteDetailsViewState
import com.varunbarad.skeleton.android.util.Event
import com.varunbarad.skeleton.android.util.ThreadSchedulers
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
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
        this.noteDetailsPresenter.onStop()
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testLoaderShownFirstWhenScreenStarts() {
        `when`(notesRepository.getNoteDetails(noteId)).thenReturn(Observable.just(note))
        `when`(noteDetailsView.onButtonBookmarkNoteClick()).thenReturn(Observable.never())

        noteDetailsPresenter.onStart()

        verify(noteDetailsView).updateScreen(
            NoteDetailsViewState(
                noteTitleText = "",
                noteContentText = "",
                noteTimestampText = "",
                isLoaderVisible = true,
                isStarFilled = false
            )
        )
    }

    @Test
    fun testNoteDetailsShownAfterLoader() {
        `when`(notesRepository.getNoteDetails(noteId)).thenReturn(Observable.just(note))
        `when`(noteDetailsView.onButtonBookmarkNoteClick()).thenReturn(Observable.never())

        noteDetailsPresenter.onStart()

        verify(noteDetailsView).updateScreen(
            NoteDetailsViewState(
                noteTitleText = "",
                noteContentText = "",
                noteTimestampText = "",
                isLoaderVisible = true,
                isStarFilled = false
            )
        )
        verify(noteDetailsView).updateScreen(
            NoteDetailsViewState(
                noteTitleText = note.title,
                noteContentText = note.contents,
                noteTimestampText = formattedTimestampText,
                isLoaderVisible = false,
                isStarFilled = note.isBookmarked
            )
        )
    }

    @Test
    fun testNoteDetailsUpdatedInRepositoryWhenBookmarkNoteButtonIsClicked() {
        `when`(notesRepository.getNoteDetails(noteId)).thenReturn(Observable.just(note, note))
        `when`(noteDetailsView.onButtonBookmarkNoteClick()).thenReturn(Observable.just(Event.IGNORE))

        noteDetailsPresenter.onStart()

        verify(notesRepository, times(1)).updateNote(
            note.copy(isBookmarked = !note.isBookmarked)
        )
    }

    @Test
    fun testNoteDetailsNotUpdatedInRepositoryWhenBookmarkNoteButtonIsNotClicked() {
        `when`(notesRepository.getNoteDetails(noteId)).thenReturn(Observable.just(note))
        `when`(noteDetailsView.onButtonBookmarkNoteClick()).thenReturn(Observable.never())

        noteDetailsPresenter.onStart()

        verify(notesRepository, never()).updateNote(notNull())
    }

    private val noteId: Long = 1
    private val note: DbNote = DbNote(
        id = noteId,
        title = "Note title",
        contents = "Note contents go here",
        timestamp = Date(System.currentTimeMillis()),
        isBookmarked = false
    )
    private val formattedTimestampText: String = SimpleDateFormat(
        "dd LLL yyyy, hh:mm a",
        Locale.getDefault()
    ).format(note.timestamp!!) // We know that timestamp is not null here since we initialized it just above
}
