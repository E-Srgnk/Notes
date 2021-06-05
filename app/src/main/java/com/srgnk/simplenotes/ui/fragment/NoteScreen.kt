package com.srgnk.simplenotes.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.srgnk.simplenotes.R
import com.srgnk.simplenotes.mvp.presenter.NotePresenter
import com.srgnk.simplenotes.mvp.view.NoteView
import com.srgnk.simplenotes.ui.activity.AppActivity
import kotlinx.android.synthetic.main.fragment_main.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class NoteScreen: MvpAppCompatFragment(R.layout.fragment_note), NoteView {

    @InjectPresenter
    lateinit var presenter: NotePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppActivity).setSupportActionBar(toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.clickedArrowHome()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.save_note).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun showFragment(screen: Fragment) {
        (activity as AppActivity).showFragment(screen)
    }

}