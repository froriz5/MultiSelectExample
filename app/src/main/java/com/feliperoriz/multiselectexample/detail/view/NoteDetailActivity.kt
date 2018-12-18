package com.feliperoriz.multiselectexample.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.feliperoriz.multiselectexample.R
import com.feliperoriz.multiselectexample.detail.viewmodel.NoteDetailViewModel
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.NotesDatabase
import kotlinx.android.synthetic.main.activity_notes_detail.*

private const val EXTRA_NOTE_KEY = "extra_note_key"

class NoteDetailActivity: AppCompatActivity() {

    companion object {

        @JvmStatic
        fun getIntent(origin: Context, key: Long): Intent {
            return Intent(origin, NoteDetailActivity::class.java)
                    .putExtra(EXTRA_NOTE_KEY, key)
        }
    }

    private val repository: NotesRepository by lazy(LazyThreadSafetyMode.NONE) {
        val repoDb = NotesDatabase.getInstance(this).notesDao()
        NotesRepository(repoDb)
    }

    private val viewModel: NoteDetailViewModel by lazy(LazyThreadSafetyMode.NONE) {
        val vm = ViewModelProviders.of(this)[NoteDetailViewModel::class.java]
        vm.setRepo(repository)
        vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_detail)

        setup()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setup() {
        setupToolbar()
        setupObservers()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun setupObservers() {
        val noteKey = intent.getLongExtra(EXTRA_NOTE_KEY, -1)
        if (noteKey == -1L) {
            finish()
            return
        }

        viewModel.getNoteLiveData(noteKey).observe(this, Observer { note ->
            note?.run {
                title_view.text = title

                val description = if (body.isNotEmpty()) body else "No description"
                description_view.text = description
            }
        })
    }
}