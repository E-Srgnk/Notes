package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.model.NoteDatabase
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.fragment.NoteScreen
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val router: Router,
    private val db: NoteDatabase
) : MvpPresenter<MainView>() {

    fun viewResume() {
        getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.initAdapter(it)
            }
    }

    fun clickedFubButton() {
        router.navigateTo(FragmentScreen { NoteScreen() })
    }

    fun clickedRecyclerItem(noteId: Long) {
        getNoteById(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { note ->
                router.navigateTo(FragmentScreen { NoteScreen(note) })
            }
    }

    fun searchNotes(request: String) {
        getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notes ->
                if (request.isBlank()) viewState.initAdapter(notes)
                else {
                    val foundNotes = notes.filter { it.title.contains(request, ignoreCase = true) }
                    viewState.initAdapter(foundNotes.toMutableList())
                }
            }
    }

    private fun getAllNotes(): Observable<MutableList<Note>> {
        return Observable.create { subscriber ->
            val notes = db.noteDao().getAllNotes()
            subscriber.onNext(notes)
            subscriber.onComplete()
        }
    }

    private fun getNoteById(noteId: Long): Observable<Note> {
        return Observable.create { subscriber ->
            val note = db.noteDao().getNoteById(noteId)
            subscriber.onNext(note)
            subscriber.onComplete()
        }
    }
}