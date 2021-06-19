package com.srgnk.simplenotes.ui.activity

import android.os.Bundle
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.AppPresenter
import com.srgnk.simplenotes.mvp.view.AppView
import com.srgnk.simplenotes.ui.navigation.CustomNavigator
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class AppActivity : MvpAppCompatActivity(), AppView {

    @Inject
    lateinit var presenterProvider: Provider<AppPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var navigationHolder: NavigatorHolder
    private val navigator: Navigator = CustomNavigator(this, R.id.appFrame)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}