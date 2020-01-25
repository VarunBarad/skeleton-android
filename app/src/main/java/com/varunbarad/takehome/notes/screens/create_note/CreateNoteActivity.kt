package com.varunbarad.takehome.notes.screens.create_note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.varunbarad.takehome.notes.R
import com.varunbarad.takehome.notes.databinding.ActivityCreateNoteBinding

class CreateNoteActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, CreateNoteActivity::class.java))
        }
    }

    private lateinit var viewBinding: ActivityCreateNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_create_note
        )

        this.setSupportActionBar(this.viewBinding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
