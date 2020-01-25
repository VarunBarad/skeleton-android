package com.varunbarad.takehome.notes

import com.varunbarad.takehome.notes.external_services.local_database.model.DbNote
import com.varunbarad.takehome.notes.repositories.NotesRepository
import com.varunbarad.takehome.notes.screens.create_note.CreateNotePresenter
import com.varunbarad.takehome.notes.screens.create_note.CreateNoteView
import com.varunbarad.takehome.notes.screens.create_note.CreateNoteViewState
import com.varunbarad.takehome.notes.util.Event
import com.varunbarad.takehome.notes.util.MAX_TITLE_LENGTH
import com.varunbarad.takehome.notes.util.ThreadSchedulers
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CreateNotePresenterTest {
    @Mock
    private lateinit var createNoteView: CreateNoteView
    @Mock
    private lateinit var notesRepository: NotesRepository

    private lateinit var createNotePresenter: CreateNotePresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ThreadSchedulers.enableTesting()

        this.createNotePresenter = CreateNotePresenter(
            this.createNoteView,
            this.notesRepository
        )
    }

    @After
    fun tearDown() {
        ThreadSchedulers.disableTesting()
    }

    @Test
    fun testBothInputFieldValueReadWhenSaveButtonClick() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(validNoteTitle)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(validNoteContent)

        createNotePresenter.onStart()

        verify(createNoteView).getTitleEditTextValue()
        verify(createNoteView).getContentsEditTextValue()
    }

    @Test
    fun testNoInputFieldValueReadWhenSaveButtonNotClick() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.never())
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(validNoteTitle)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(validNoteContent)

        createNotePresenter.onStart()

        verify(createNoteView, never()).getTitleEditTextValue()
        verify(createNoteView, never()).getContentsEditTextValue()
    }

    @Test
    fun testNoteDetailsScreenOpenWhenSaveNoteButtonClickWithValidInputs() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(validNoteTitle)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(validNoteContent)
        `when`(
            notesRepository.insertNewNote(
                DbNote(
                    title = validNoteTitle,
                    contents = validNoteContent
                )
            )
        ).thenReturn(Single.just(validNoteId))

        createNotePresenter.onStart()

        verify(createNoteView).openNoteDetailsScreen(validNoteId)
    }

    @Test
    fun testNoteDetailsScreenNotOpenWhenSaveNoteButtonNotClick() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.never())

        createNotePresenter.onStart()

        verify(createNoteView, never()).openNoteDetailsScreen(validNoteId)
    }

    @Test
    fun testNoteDetailsScreenNotOpenWhenTitleIsEmpty() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn("")
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(validNoteContent)

        createNotePresenter.onStart()

        verify(createNoteView, never()).openNoteDetailsScreen(validNoteId)
    }

    @Test
    fun testNoteDetailsScreenNotOpenWhenContentIsEmpty() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(validNoteTitle)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn("")

        createNotePresenter.onStart()

        verify(createNoteView, never()).openNoteDetailsScreen(validNoteId)
    }

    @Test
    fun testNoteDetailsScreenNotOpenWhenBothTitleAndContentIsEmpty() {
        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn("")
        `when`(createNoteView.getContentsEditTextValue()).thenReturn("")

        createNotePresenter.onStart()

        verify(createNoteView, never()).openNoteDetailsScreen(validNoteId)
    }

    @Test
    fun testTitleErrorShownWhenOnlyTitleIsEmpty() {
        val titleValue = ""
        val contentValue = validNoteContent

        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(titleValue)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(contentValue)

        createNotePresenter.onStart()

        verify(createNoteView).updateScreen(
            CreateNoteViewState(
                titleValue = titleValue,
                contentsValue = contentValue,
                titleErrorText = "Title cannot be blank",
                contentsErrorText = "",
                showTitleError = true,
                showContentsError = false
            )
        )
    }

    @Test
    fun testContentErrorShownWhenOnlyContentIsEmpty() {
        val titleValue = validNoteTitle
        val contentValue = ""

        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(titleValue)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(contentValue)

        createNotePresenter.onStart()

        verify(createNoteView).updateScreen(
            CreateNoteViewState(
                titleValue = titleValue,
                contentsValue = contentValue,
                titleErrorText = "",
                contentsErrorText = "Content cannot be blank",
                showTitleError = false,
                showContentsError = true
            )
        )
    }

    @Test
    fun testBothTitleAndContentErrorShownWhenBothTitleAndContentIsEmpty() {
        val titleValue = ""
        val contentValue = ""

        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(titleValue)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(contentValue)

        createNotePresenter.onStart()

        verify(createNoteView).updateScreen(
            CreateNoteViewState(
                titleValue = titleValue,
                contentsValue = contentValue,
                titleErrorText = "Title cannot be blank",
                contentsErrorText = "Content cannot be blank",
                showTitleError = true,
                showContentsError = true
            )
        )
    }

    @Test
    fun testTitleErrorShownWhenTitleIsTooLong() {
        val titleValue = invalidExtraLongNoteTitle
        val contentValue = validNoteContent

        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(titleValue)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(contentValue)

        createNotePresenter.onStart()

        verify(createNoteView).updateScreen(
            CreateNoteViewState(
                titleValue = titleValue,
                contentsValue = contentValue,
                titleErrorText = "Title cannot be longer than $MAX_TITLE_LENGTH characters",
                contentsErrorText = "",
                showTitleError = true,
                showContentsError = false
            )
        )
    }

    @Test
    fun testInsertNoteToRepositoryWhenTitleAndContentIsValid() {
        val titleValue = validNoteTitle
        val contentValue = validNoteContent

        `when`(createNoteView.onButtonSaveNoteClick()).thenReturn(Observable.just(Event.IGNORE))
        `when`(createNoteView.getTitleEditTextValue()).thenReturn(titleValue)
        `when`(createNoteView.getContentsEditTextValue()).thenReturn(contentValue)

        createNotePresenter.onStart()

        verify(notesRepository, times(1)).insertNewNote(
            DbNote(
                title = titleValue,
                contents = contentValue
            )
        )
    }

    private val validNoteId: Long = 1

    private val validNoteTitle = "This is a valid title"
    private val validNoteContent = "This is a valid note content"

    private val invalidExtraLongNoteTitle =
        "Yeah, sure, I mean, if you spend all day shuffling words around, you can make anything sound bad. That's because losers look stuff up while the rest of us are carp'en all them 'diems. That just sounds like slavery with extra steps. You know who's into dragons, Morty? Nerds who refuse to admit they're Christian. Ooohhh can do."
}
