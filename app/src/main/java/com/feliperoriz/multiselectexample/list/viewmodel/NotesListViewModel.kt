package com.feliperoriz.multiselectexample.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.feliperoriz.multiselectexample.repository.NotesRepository
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NotesListViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val dataSourceLiveData: LiveData<PagedList<NoteEntity>> by lazy(LazyThreadSafetyMode.NONE) {
        repository.notesDataSource
    }

    val selectNotesCountLiveData: LiveData<Int> by lazy(LazyThreadSafetyMode.NONE) {
        repository.selectNotesCountLiveData
    }

    private lateinit var repository: NotesRepository

    fun setRepo(repository: NotesRepository) {
        this.repository = repository
    }

    fun deleteSelectedNotes(callback: () -> Unit) {
        val disposable = repository.deleteAllSelectedCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { callback() },
                        {
                            Log.d("NotesListViewModel", "Failed to bulk delete selected notes.")
                            callback()
                        }
                )
    }
}