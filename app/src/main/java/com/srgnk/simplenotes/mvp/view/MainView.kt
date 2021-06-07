package com.srgnk.simplenotes.mvp.view

import com.srgnk.simplenotes.mvp.model.Note
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun initAdapter(items: MutableList<Note>)
}