package com.srgnk.simplenotes.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(AddToEndSingleStrategy::class)
interface NoteView: MvpView {

    @Skip
    fun setTitle(title: String)

    @Skip
    fun setContent(content: String)

    fun setDate(date: String)

    fun btnSaveVisible(visible: Boolean)

    fun showDialogDeleteNote()

    fun hideDialogDeleteNote()

    fun showKeyboard()

    fun hideKeyboard()

    @Skip
    fun showMessage(message: Int)
}