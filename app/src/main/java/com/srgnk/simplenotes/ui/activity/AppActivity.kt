package com.srgnk.simplenotes.ui.activity

import android.os.Bundle
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.AppPresenter
import com.srgnk.simplenotes.mvp.view.AppView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class AppActivity : MvpAppCompatActivity(), AppView {

    @InjectPresenter
    lateinit var presenter: AppPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}