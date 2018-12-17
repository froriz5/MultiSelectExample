package com.feliperoriz.multiselectexample.repository.db

import androidx.paging.DataSource
import androidx.room.*
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity
import io.reactivex.Completable

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity): Completable

    @Query("DELETE FROM notes WHERE `key` IN (:noteKeys)")
    fun delete(noteKeys: List<Long>)

    @Query("DELETE FROM notes WHERE is_selected = 1")
    fun deleteAllSelected()

    @Query("SELECT * FROM notes")
    fun getNotes(): DataSource.Factory<Long, NoteEntity>
}