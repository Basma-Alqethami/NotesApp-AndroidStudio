package com.example.notesapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.Model.NoteViewModel
import com.example.notesapp.R
import com.example.notesapp.Room.Data
import com.example.notesapp.Room.NotesDatabase
import kotlinx.coroutines.*

class ShowNotes : AppCompatActivity() {

    private lateinit var et_Title: EditText
    private lateinit var et_Note: EditText
    private lateinit var btnUpdate: Button

    lateinit var viewModel: NoteViewModel

    private var id = 0
    var title11 = ""
    var note11 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_notes)

        et_Title = findViewById(R.id.et_Title)
        et_Note = findViewById(R.id.et_Note)
        btnUpdate = findViewById(R.id.btnUpdate)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val disData = intent.getSerializableExtra("displayData") as Data
        id = disData.id
        et_Title.setText(disData.title)
        et_Note.setText(disData.note)

        btnUpdate.setOnClickListener {

            title11 = et_Title.text.toString()
            note11 = et_Note.text.toString()

            val noteData = viewModel.update(Data(id, title11, note11))
            if (noteData != null) {
                val intent = Intent(this@ShowNotes, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_and_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.BackMenu1 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.DeleteMenu -> {

                title11 = et_Title.text.toString()
                note11 = et_Note.text.toString()

                val noteData = viewModel.delete(Data(id, title11, note11))
                if (noteData != null) {
                    val intent = Intent(this@ShowNotes, MainActivity::class.java)
                    startActivity(intent)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}