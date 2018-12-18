package com.feliperoriz.multiselectexample.add.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

import com.feliperoriz.multiselectexample.R
import com.feliperoriz.multiselectexample.add.viewmodel.NoteAddViewModel
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.NotesDatabase
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_note_add.*

class NoteAddActivity: AppCompatActivity() {

    private val repository: NotesRepository by lazy(LazyThreadSafetyMode.NONE) {
        val notesDao = NotesDatabase.getInstance(this).notesDao()
        NotesRepository(notesDao)
    }

    private val viewModel: NoteAddViewModel by lazy(LazyThreadSafetyMode.NONE) {
        val vm = ViewModelProviders.of(this)[NoteAddViewModel::class.java]
        vm.setRepo(repository)
        vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add -> {
                val title = title_edit_text.text?.toString()
                val description = description_edit_text.text?.toString() ?: ""

                if (title.isNullOrEmpty()) {
                    Snackbar.make(root_view, "Title cannot be empty", Snackbar.LENGTH_SHORT).show()
                } else {
                    viewModel.addNote(title, description) {
                        finish()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setup() {
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }
}