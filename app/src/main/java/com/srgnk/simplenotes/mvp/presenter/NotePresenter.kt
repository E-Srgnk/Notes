package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.model.NoteDatabase
import com.srgnk.simplenotes.mvp.utils.getFormattedDate
import com.srgnk.simplenotes.mvp.view.NoteView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class NotePresenter @Inject constructor(
    private val router: Router,
    private val db: NoteDatabase
) : MvpPresenter<NoteView>() {

    private val createDate: Date by lazy { Date() }

    private var note: Note? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showKeyboard()
        viewState.setDate(getFormattedDate(createDate))
    }

    fun clickedArrowHome() {
        viewState.hideKeyboard()
        router.exit()
    }

    fun clickedSaveNote(title: String, content: String) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                note = Note(title, content, createDate.time)
                val noteId = it.noteDao().insert(note!!)
                note?.id = noteId
            }
        viewState.showMessage(R.string.note_saved)
        viewState.btnSaveVisible(false)
    }

    fun clickedDeleteNote() {
        note?.let { note ->
            Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    it.noteDao().delete(note)
                }
        }
        viewState.showMessage(R.string.note_deleted)
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

    private fun isNewNote() = note == null

    private fun noteIsNotEmpty(title: String, content: String) =
        title.isNotEmpty() || content.isNotEmpty()

    private fun noteWasChanged(title: String, content: String) =
        title != note?.title || content != note?.content
}