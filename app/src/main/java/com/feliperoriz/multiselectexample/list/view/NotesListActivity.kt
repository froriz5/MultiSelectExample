package com.feliperoriz.multiselectexample.list.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.feliperoriz.multiselectexample.R
import com.feliperoriz.multiselectexample.add.view.NoteAddActivity
import com.feliperoriz.multiselectexample.detail.view.NoteDetailActivity
import com.feliperoriz.multiselectexample.list.view.recyclerview.NotesAdapter
import com.feliperoriz.multiselectexample.list.view.recyclerview.NotesItemCallback
import com.feliperoriz.multiselectexample.list.viewmodel.NotesListViewModel
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.NotesDatabase

import kotlinx.android.synthetic.main.activity_notes_list.*

class NotesListActivity : AppCompatActivity(), ActionMode.Callback {

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                viewModel.deleteSelectedNotes {
                    mode.finish()
                }
                true
            }
            else -> false
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.bulk_mode_menu, menu)
        mode.title = "Select notes"

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        val selectedNotesCount = viewModel.selectNotesCountLiveData.value ?: 0
        if (selectedNotesCount > 0) {
            mode.title = "$selectedNotesCount notes selected"
        } else {
            mode.title = "Select notes"
        }

        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        actionMode = null
        notesAdapter.isBulkModeEnabled = false
    }

    private val repository: NotesRepository by lazy(LazyThreadSafetyMode.NONE) {
        val repoDb = NotesDatabase.getInstance(this).notesDao()
        NotesRepository(repoDb)
    }

    private val viewModel: NotesListViewModel by lazy(LazyThreadSafetyMode.NONE) {
        val vm = ViewModelProviders.of(this)[NotesListViewModel::class.java]
        vm.setRepo(repository)
        vm
    }

    private val notesAdapter: NotesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        val layoutInflater = LayoutInflater.from(this)
        val itemCallback = NotesItemCallback()
        NotesAdapter(layoutInflater, itemCallback)
    }

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        setup()
    }

    private fun setup() {
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@NotesListActivity)
            adapter = notesAdapter
        }

        add_fab.setOnClickListener {
            val intent = Intent(this, NoteAddActivity::class.java)
            startActivity(intent)
        }

        setupToolbar()
        setupAdapter()
        setupObservers()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupAdapter() {
        notesAdapter.onClickNote = { note ->
            val intent = NoteDetailActivity.getIntent(this, note.key!!)
            startActivity(intent)
        }

        notesAdapter.onBulkModeStarted = {
            add_fab.hide()
            actionMode = startSupportActionMode(this)
        }

        notesAdapter.onBulkModeEnded = {
            add_fab.show()
        }
    }

    private fun setupObservers() {
        viewModel.dataSourceLiveData.observe(this, Observer { pagedList ->
            notesAdapter.submitList(pagedList)
        })
        viewModel.selectNotesCountLiveData.observe(this, Observer {
            actionMode?.invalidate()
        })
    }

}
