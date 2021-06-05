package com.srgnk.simplenotes.ui.fragment

import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.NotePresenter
import com.srgnk.simplenotes.mvp.view.NoteView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class NoteScreen: MvpAppCompatFragment(R.layout.fragment_note), NoteView {

    @InjectPresenter
    lateinit var presenter: NotePresenter

}