package com.feliperoriz.multiselectexample.list.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.feliperoriz.multiselectexample.R
import com.feliperoriz.multiselectexample.add.view.NoteAddActivity
import com.feliperoriz.multiselectexample.detail.NoteDetailActivity
import com.feliperoriz.multiselectexample.list.view.recyclerview.NotesAdapter
import com.feliperoriz.multiselectexample.list.view.recyclerview.NotesItemCallback
import com.feliperoriz.multiselectexample.list.viewmodel.NotesListViewModel
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.NotesDatabase

import kotlinx.android.synthetic.main.activity_notes_list.*

class NotesListActivity : AppCompatActivity() {

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

//        notesAdapter.onClickNote = { note ->
//            val intent = NoteDetailActivity.getIntent(this, note.key!!)
//            startActivity(intent)
//        }

        add_fab.setOnClickListener {
            val intent = Intent(this, NoteAddActivity::class.java)
            startActivity(intent)
        }

        setupToolbar()
        setupObservers()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupObservers() {
        viewModel.dataSourceLiveData.observe(this, Observer { pagedList ->
            notesAdapter.submitList(pagedList)
        })
    }

}
