package com.feliperoriz.multiselectexample.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(@PrimaryKey(autoGenerate = true) val key: Long? = null,
                      @ColumnInfo(name = "title") val title: String = "",
                      @ColumnInfo(name = "body") val body: String = "",

                      @ColumnInfo(name = "is_selected") val isSelected: Boolean = false)