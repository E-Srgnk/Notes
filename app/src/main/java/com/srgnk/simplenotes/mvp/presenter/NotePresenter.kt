package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.model.NoteDatabase
import com.srgnk.simplenotes.utils.getFormattedDate
import com.srgnk.simplenotes.mvp.view.NoteView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class NotePresenter @Inject constructor(
    private var note: Note?,
    private val router: Router,
    private val db: NoteDatabase
) : MvpPresenter<NoteView>() {

    private val createDate: Date by lazy { Date() }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        note?.let {
            viewState.setTitle(it.title)
            viewState.setContent(it.content)
            viewState.setDate(getFormattedDate(Date(it.date)))
        } ?: viewState.setDate(getFormattedDate(createDate))
        viewState.showKeyboard()
    }

    fun clickedSaveNote(title: String, content: String) {
        if (noteIsNotEmpty(title, content)) {
            saveNote(title, content)
            viewState.showMessage(R.string.note_saved)
            viewState.btnSaveVisible(false)
        } else {
            deleteNote()
            router.exit()
        }
    }

    private fun saveNote(title: String, content: String) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                if (isNewNote()) {
                    note = Note(title, content, createDate.time)
                } else {
                    note?.title = title
                    note?.content = content
                    note?.date = Date().time
                }
                val noteId = it.noteDao().insert(note!!)
                note?.id = noteId
            }
    }

    fun clickedDeleteNote() {
        if (isNewNote()) {
            viewState.hideKeyboard()
            router.exit()
        }
        viewState.showDialogDeleteNote()
    }

    private fun deleteNote() {
        note?.let { note ->
            Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    it.noteDao().delete(note)
                }
        }
    }

    fun clickedArrowHome() {
        viewState.hideKeyboard()
        router.exit()
    }

    fun textChanged(title: String, content: String) {
        if ((isNewNote() && noteIsNotEmpty(title, content))
            || (!isNewNote() && noteWasChanged(title, content))
        ) {
            viewState.btnSaveVisible(true)
        } else {
            viewState.btnSaveVisible(false)
        }
    }

    fun confirmDeletionNote() {
        deleteNote()
        viewState.showMessage(R.string.note_deleted)
        viewState.hideDialogDeleteNote()
        viewState.hideKeyboard()
        router.exit()
    }

    fun cancelDeletionNote() {
        viewState.hideDialogDeleteNote()
    }

    fun dismissDialog() {
        viewState.hideDialogDeleteNote()
    }

    private fun isNewNote() = note == null

    private fun noteIsNotEmpty(title: String, content: String) =
        title.isNotEmpty() || content.isNotEmpty()

    private fun noteWasChanged(title: String, content: String) =
        title != note?.title || content != note?.content
}