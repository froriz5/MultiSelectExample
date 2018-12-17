package com.feliperoriz.multiselectexample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.feliperoriz.multiselectexample.repository.db.NotesDao
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity
import io.reactivex.Completable

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class NotesRepository(private val notesDao: NotesDao,
                      private val ioScheduler: Scheduler) {

    val notesDataSource: LiveData<PagedList<NoteEntity>>

    init {
        val dataSource = notesDao.getNotes()
        notesDataSource = LivePagedListBuilder(dataSource, 20).build()
    }

    fun add(title: String, body: String): Disposable {
        val note = NoteEntity(title = title, body = body)
        return notesDao.insert(note)
                .subscribeOn(ioScheduler)
                .subscribe(
                        { Log.d("NotesRepository", "Successfully added note: $note") },
                        { Log.d("NotesRepository", "Failed to add note: $note") }
                )
    }

    fun delete(key: Long): Disposable {
        return Completable.fromAction { notesDao.delete(listOf(key)) }
                .subscribeOn(ioScheduler)
                .subscribe(
                        { Log.d("NotesRepository", "Successfully deleted note with id: $key") },
                        { Log.d("NotesRepository", "Failed to delete note with id: $key") }
                )
    }

    fun deleteAllSelected(): Disposable {
        return Completable.fromAction { notesDao.deleteAllSelected() }
                .subscribeOn(ioScheduler)
                .subscribe(
                        { Log.d("NotesRepository", "Successfully deleted selected notes.") },
                        { Log.d("NotesRepository", "Failed to delete selected notes.") }
                )
    }
}