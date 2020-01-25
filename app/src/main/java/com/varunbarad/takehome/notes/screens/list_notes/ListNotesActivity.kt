package com.varunbarad.takehome.notes.screens.list_notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.varunbarad.takehome.notes.R
import com.varunbarad.takehome.notes.databinding.ActivityListNotesBinding

class ListNotesActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityListNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_notes)
        this.setSupportActionBar(this.viewBinding.toolbar)

        this.viewBinding.buttonNewNote.setOnClickListener {
            Toast.makeText(
                this,
                "Open screen to create new note",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
