package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.srgnk.simplenotes.mvp.view.AppView
import com.srgnk.simplenotes.ui.fragment.MainScreen
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class AppPresenter @Inject constructor(private val router: Router) : MvpPresenter<AppView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(FragmentScreen { MainScreen() })
    }
}