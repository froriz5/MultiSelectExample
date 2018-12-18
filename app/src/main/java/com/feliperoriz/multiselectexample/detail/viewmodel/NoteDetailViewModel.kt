package com.feliperoriz.multiselectexample.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity

class NoteDetailViewModel: ViewModel() {

    private lateinit var repository: NotesRepository

    fun setRepo(repository: NotesRepository) {
        this.repository = repository
    }

    fun getNoteLiveData(key: Long): LiveData<NoteEntity> = repository.getNoteLiveData(key)
}