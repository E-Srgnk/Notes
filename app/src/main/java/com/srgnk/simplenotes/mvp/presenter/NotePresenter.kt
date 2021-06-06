package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.srgnk.simplenotes.mvp.view.NoteView
import com.srgnk.simplenotes.ui.fragment.MainScreen
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NotePresenter @Inject constructor(private val router: Router): MvpPresenter<NoteView>() {

    fun clickedArrowHome() {
        router.exit()
    }
}