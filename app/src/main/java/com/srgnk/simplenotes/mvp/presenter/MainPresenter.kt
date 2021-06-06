package com.srgnk.simplenotes.mvp.presenter

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.fragment.NoteScreen
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val router: Router): MvpPresenter<MainView>() {

    fun clickedFubButton() {
        router.navigateTo(FragmentScreen { NoteScreen() })
    }
}