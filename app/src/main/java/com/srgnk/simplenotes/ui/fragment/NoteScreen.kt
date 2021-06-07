package com.srgnk.simplenotes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.NotePresenter
import com.srgnk.simplenotes.mvp.view.NoteView
import com.srgnk.simplenotes.ui.activity.AppActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.toolbar
import kotlinx.android.synthetic.main.fragment_note.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class NoteScreen : MvpAppCompatFragment(R.layout.fragment_note), NoteView {

    @Inject
    lateinit var providePresenter: Provider<NotePresenter>
    private val presenter by moxyPresenter { providePresenter.get() }

    private var menu: Menu? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppActivity).setSupportActionBar(toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        title.addTextChangedListener {
            presenter.textChanged(currentTitle(), currentContent()) }
        content.addTextChangedListener {
            presenter.textChanged(currentTitle(), currentContent()) }

        title.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        inflater.inflate(R.menu.note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.clickedArrowHome()
            R.id.save_note -> presenter.clickedSaveNote(currentTitle(), currentContent())
            R.id.delete_note -> presenter.clickedDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.save_note).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    private fun currentTitle() = title.text.toString()
    private fun currentContent() = content.text.toString()

    override fun showKeyboard() {
        activity?.let {
            (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
        }
    }

    override fun hideKeyboard() {
        val _activity = activity
        val _view = view
        if (_activity != null && _view != null) {
            (_activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(_view.windowToken, 0)
        }
    }

    override fun btnSaveVisible(visible: Boolean) {
        menu?.let { it.findItem(R.id.save_note).isVisible = visible }
    }

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}