package com.feliperoriz.multiselectexample.add.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.feliperoriz.multiselectexample.repository.NotesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoteAddViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var repository: NotesRepository

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setRepo(repository: NotesRepository) {
        this.repository = repository
    }

    fun addNote(title: String, description: String, callback: () -> Unit) {
        val disposable = repository.addCompletable(title, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { callback() },
                        {
                            Log.d("Add Note", "Failed to add note: ${it.localizedMessage}")
                            callback()
                        }
                )
        compositeDisposable.add(disposable)
    }
}