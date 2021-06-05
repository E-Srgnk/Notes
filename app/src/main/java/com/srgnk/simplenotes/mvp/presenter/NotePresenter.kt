package com.srgnk.simplenotes.mvp.presenter

import com.srgnk.simplenotes.mvp.view.NoteView
import com.srgnk.simplenotes.ui.fragment.MainScreen
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class NotePresenter: MvpPresenter<NoteView>() {

    fun clickedArrowHome() {
        viewState.showFragment(MainScreen())
    }

}