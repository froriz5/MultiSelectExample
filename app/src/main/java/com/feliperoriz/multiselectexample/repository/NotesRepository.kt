package com.feliperoriz.multiselectexample.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.feliperoriz.multiselectexample.repository.db.NotesDao
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity

import io.reactivex.Completable

class NotesRepository(private val notesDao: NotesDao) {

    val notesDataSource: LiveData<PagedList<NoteEntity>>

    init {
        val dataSource = notesDao.getNotes()
        notesDataSource = LivePagedListBuilder(dataSource, 20).build()
    }

    fun addCompletable(title: String, body: String): Completable {
        val note = NoteEntity(title = title, body = body)
        return notesDao.insertCompletable(note)
    }

    fun deleteCompletable(key: Long): Completable {
        return Completable.fromAction { notesDao.delete(listOf(key)) }
    }

    fun deleteAllSelectedCompletable(): Completable {
        return Completable.fromAction { notesDao.deleteAllSelected() }
    }
}