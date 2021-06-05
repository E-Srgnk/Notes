package com.srgnk.simplenotes.ui.fragment

import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.MainPresenter
import com.srgnk.simplenotes.mvp.view.MainView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class MainScreen: MvpAppCompatFragment(R.layout.fragment_main), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

}