package com.srgnk.simplenotes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.presenter.MainPresenter
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.activity.AppActivity
import com.srgnk.simplenotes.ui.adapter.RecyclerAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class MainScreen : MvpAppCompatFragment(R.layout.fragment_main),
    RecyclerAdapter.RecyclerViewClickListener, MainView {

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppActivity).setSupportActionBar(toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        addNewNote.setOnClickListener {
            presenter.clickedFubButton()
        }
    }

    override fun initAdapter(items: MutableList<Note>) {
        val adapter = RecyclerAdapter(items, this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = GridLayoutManager(context, 3)
    }

    override fun recyclerViewClickListener(noteId: Long) {
        presenter.clickedRecyclerItem(noteId)
    }
}