package com.srgnk.simplenotes.mvp.presenter

import com.srgnk.simplenotes.mvp.view.AppView
import com.srgnk.simplenotes.ui.fragment.MainScreen
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class AppPresenter: MvpPresenter<AppView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showFragment(MainScreen())
    }

}