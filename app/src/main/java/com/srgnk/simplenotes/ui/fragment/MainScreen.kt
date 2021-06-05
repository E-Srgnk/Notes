package com.srgnk.simplenotes.ui.fragment

import android.os.Bundle
import android.view.View
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.MainPresenter
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.activity.AppActivity
import kotlinx.android.synthetic.main.fragment_main.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class MainScreen: MvpAppCompatFragment(R.layout.fragment_main), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppActivity).setSupportActionBar(toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}