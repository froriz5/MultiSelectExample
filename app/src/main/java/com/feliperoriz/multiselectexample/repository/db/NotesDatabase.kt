package com.feliperoriz.multiselectexample.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity

private const val DATABASE_NAME = "notes.db"

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {

        private var INSTANCE: NotesDatabase? = null

        private val lock = Any()

        @JvmStatic
        fun getInstance(context: Context): NotesDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            NotesDatabase::class.java, DATABASE_NAME)
                            .build()
                }
                return INSTANCE!!
            }
        }
    }


}