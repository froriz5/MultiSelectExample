package com.feliperoriz.multiselectexample.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity

class NotesListViewModel: ViewModel() {

    val dataSourceLiveData: LiveData<PagedList<NoteEntity>> by lazy(LazyThreadSafetyMode.NONE) {
        repository.notesDataSource
    }

    private lateinit var repository: NotesRepository

    fun setRepo(repository: NotesRepository) {
        this.repository = repository
    }
}