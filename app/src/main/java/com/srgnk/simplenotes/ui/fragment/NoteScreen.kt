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
import com.srgnk.simplenotes.databinding.FragmentNoteBinding
import com.srgnk.simplenotes.mvp.model.Note
import com.srgnk.simplenotes.mvp.model.NoteDatabase
import com.srgnk.simplenotes.mvp.presenter.NotePresenter
import com.srgnk.simplenotes.mvp.view.NoteView
import com.srgnk.simplenotes.ui.activity.AppActivity
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class NoteScreen(private var note: Note? = null) : MvpAppCompatFragment(R.layout.fragment_note),
    NoteView {

    private var noteBinding: FragmentNoteBinding? = null
    private val binding get() = noteBinding!!

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteBinding = FragmentNoteBinding.bind(view)

        (activity as AppActivity).setSupportActionBar(binding.toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.titleNote.addTextChangedListener {
            presenter.textChanged(currentTitle(), currentContent())
        }
        binding.contentNote.addTextChangedListener {
            presenter.textChanged(currentTitle(), currentContent())
        }

        binding.titleNote.requestFocus()
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

    private fun currentTitle() = binding.titleNote.text.toString()
    private fun currentContent() = binding.contentNote.text.toString()

    override fun setTitle(title: String) {
        binding.titleNote.append(title)
    }

    override fun setContent(content: String) {
        binding.contentNote.append(content)
    }

    override fun setDate(date: String) {
        binding.dateNote.text = date
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
        noteBinding = null
        super.onDestroy()

        dialogDeleteNote?.setOnDismissListener(null)
        dialogDeleteNote?.dismiss()
    }
}