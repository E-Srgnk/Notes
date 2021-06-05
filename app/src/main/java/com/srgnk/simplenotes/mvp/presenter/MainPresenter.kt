package com.srgnk.simplenotes.mvp.presenter

import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.fragment.NoteScreen
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter: MvpPresenter<MainView>() {

    fun clickedFubButton() {
        viewState.showFragment(NoteScreen())
    }

}