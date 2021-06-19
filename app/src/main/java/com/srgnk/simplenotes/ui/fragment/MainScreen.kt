package com.srgnk.simplenotes.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.databinding.FragmentMainBinding
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.presenter.MainPresenter
import com.srgnk.simplenotes.mvp.view.MainView
import com.srgnk.simplenotes.ui.activity.AppActivity
import com.srgnk.simplenotes.ui.adapter.RecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainScreen : MvpAppCompatFragment(R.layout.fragment_main),
    RecyclerAdapter.RecyclerViewClickListener, MainView {

    private var mainBinding: FragmentMainBinding? = null
    private val binding get() = mainBinding!!

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding = FragmentMainBinding.bind(view)

        (activity as AppActivity).setSupportActionBar(binding.toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.addNewNote.setOnClickListener {
            presenter.clickedFubButton()
        }

        binding.searchNote.setOnClickListener { binding.searchNote.isIconified = false }
        setSearchViewQueryTextListener()
    }

    private fun setSearchViewQueryTextListener() {
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            binding.searchNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        }).map { text -> text.lowercase(Locale.getDefault()).trim() }
            .debounce(350, TimeUnit.MILLISECONDS)
            .subscribe { text ->
                presenter.searchNotes(text)
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

    override fun onResume() {
        super.onResume()
        presenter.viewResume()
    }

    override fun onDestroy() {
        mainBinding = null
        super.onDestroy()
    }
}