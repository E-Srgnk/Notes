package com.srgnk.simplenotes.mvp.view

import com.srgnk.simplenotes.mvp.model.Note
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface MainView : MvpView {

    @Skip
    fun initAdapter(items: MutableList<Note>)
}