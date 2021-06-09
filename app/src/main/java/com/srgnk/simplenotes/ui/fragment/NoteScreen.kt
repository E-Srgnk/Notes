package com.srgnk.simplenotes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.github.terrakok.cicerone.Router
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.model.NoteDatabase
import com.srgnk.simplenotes.mvp.presenter.NotePresenter
import com.srgnk.simplenotes.mvp.view.NoteView
import com.srgnk.simplenotes.ui.activity.AppActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_note.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class NoteScreen(private var note: Note? = null) : MvpAppCompatFragment(R.layout.fragment_note), NoteView {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var db: NoteDatabase

    @InjectPresenter
    lateinit var presenter: NotePresenter
    @ProvidePresenter
    fun providePresenter() = NotePresenter(note, router, db)

    private var menu: Menu? = null
    private var dialogDeleteNote: AlertDialog? = null

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

        title_note.addTextChangedListener {
            presenter.textChanged(currentTitle(), currentContent()) }
        content_note.addTextChangedListener {
            presenter.textChanged(currentTitle(), currentContent()) }

        title_note.requestFocus()
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

    private fun currentTitle() = title_note.text.toString()
    private fun currentContent() = content_note.text.toString()

    override fun setTitle(title: String) {
        title_note.append(title)
    }

    override fun setContent(content: String) {
        content_note.append(content)
    }

    override fun setDate(date: String) {
        date_note.text = date
    }

    override fun btnSaveVisible(visible: Boolean) {
        menu?.let { it.findItem(R.id.save_note).isVisible = visible }
    }

    override fun showDialogDeleteNote() {
        val view = layoutInflater.inflate(R.layout.dialog_delete_note, null)
        view.findViewById<Button>(R.id.confirm_delete).setOnClickListener {
            presenter.confirmDeletionNote()
        }
        view.findViewById<Button>(R.id.cancel_delete).setOnClickListener {
            presenter.cancelDeletionNote()
        }

        dialogDeleteNote = context?.let {
            AlertDialog.Builder(it)
                .setView(view)
                .setOnDismissListener { presenter.dismissDialog() }
                .show()
        }
    }

    override fun hideDialogDeleteNote() {
        dialogDeleteNote?.dismiss()
    }

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

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        dialogDeleteNote?.setOnDismissListener(null)
        dialogDeleteNote?.dismiss()
    }
}