package com.feliperoriz.multiselectexample.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.feliperoriz.multiselectexample.R

private const val EXTRA_NOTE_KEY = "extra_note_key"

class NoteDetailActivity: AppCompatActivity() {

    companion object {

        @JvmStatic
        fun getIntent(origin: Context, key: Long): Intent {
            return Intent(origin, NoteDetailActivity::class.java)
                    .putExtra(EXTRA_NOTE_KEY, key)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_detail)
    }
}