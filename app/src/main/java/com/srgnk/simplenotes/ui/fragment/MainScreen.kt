package com.srgnk.simplenotes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.databinding.FragmentMainBinding
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.presenter.MainPresenter
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.activity.AppActivity
import com.srgnk.simplenotes.ui.adapter.RecyclerAdapter
import dagger.android.support.AndroidSupportInjection
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class MainScreen : MvpAppCompatFragment(R.layout.fragment_main),
    RecyclerAdapter.RecyclerViewClickListener, MainView {

    private var mainBinding: FragmentMainBinding? = null
    private val binding get() = mainBinding!!

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding = FragmentMainBinding.bind(view)

        (activity as AppActivity).setSupportActionBar(binding.toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.addNewNote.setOnClickListener {
            presenter.clickedFubButton()
        }

        binding.searchNote.addTextChangedListener {
            presenter.searchNotes(binding.searchNote.text.toString())
        }
    }

    override fun initAdapter(items: MutableList<Note>) {
        val adapter = RecyclerAdapter(items, this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(context, 3)
    }

    override fun recyclerViewClickListener(noteId: Long) {
        presenter.clickedRecyclerItem(noteId)
    }

    override fun onDestroy() {
        mainBinding = null
        super.onDestroy()
    }
}