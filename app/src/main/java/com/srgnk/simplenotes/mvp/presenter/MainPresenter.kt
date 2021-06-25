package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.srgnk.simplenotes.mvp.model.DatabaseRepository
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.fragment.NoteScreen
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val router: Router,
    private val repository: DatabaseRepository
) : MvpPresenter<MainView>() {

    fun viewResume() {
        presenterScope.launch {
            val notes = repository.getAllNotes()
            launch(Dispatchers.Main) {
                viewState.initAdapter(notes)
            }
        }
    }

    fun clickedFubButton() {
        router.navigateTo(FragmentScreen { NoteScreen() })
    }

    fun clickedRecyclerItem(noteId: Long) {
        presenterScope.launch {
            val note = repository.getNoteById(noteId)
            launch(Dispatchers.Main) {
                router.navigateTo(FragmentScreen { NoteScreen(note) })
            }
        }
    }

    fun searchNotes(request: String) {
        presenterScope.launch {
            val foundNotes = repository.getAllNotes()
                .filter { it.title.contains(request, ignoreCase = true) }
            launch(Dispatchers.Main) {
                viewState.initAdapter(foundNotes.toMutableList())
            }
        }
    }
}