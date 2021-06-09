package com.srgnk.simplenotes.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(AddToEndSingleStrategy::class)
interface NoteView: MvpView {

    fun setDate(date: String)

    @Skip
    fun setTitle(title: String)

    @Skip
    fun setContent(content: String)

    fun showKeyboard()

    fun hideKeyboard()

    fun btnSaveVisible(visible: Boolean)

    @Skip
    fun showMessage(message: Int)
}