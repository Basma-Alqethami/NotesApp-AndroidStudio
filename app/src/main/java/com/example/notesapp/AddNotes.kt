package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class AddNotes : AppCompatActivity() {

    private lateinit var etTitleAdd: EditText
    private lateinit var edNoteAdd: EditText
    private lateinit var btnSave: Button
    private val DB by lazy { NotesDatabase.getDatabase(this).note_Dao() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        etTitleAdd = findViewById(R.id.etTitleAdd)
        edNoteAdd = findViewById(R.id.edNoteAdd)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener { SavaData() }

    }

    private fun SavaData() {
        val textTitle = etTitleAdd.text.toString()
        val textNote = edNoteAdd.text.toString()

        try {
            if (textTitle.isNotEmpty()) {
                if (textNote.isNotEmpty()) {
                    NoteIsNotEmpty(textTitle, textNote)
                } else {
                    NoteIsEmpty(textTitle)
                }
            } else {
                Toast.makeText(this@AddNotes, "Please enter the title", Toast.LENGTH_SHORT).show()
            }
        }catch (e:IOException) {
            Toast.makeText(this@AddNotes, "something wrong please try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun NoteIsNotEmpty(textTitle: String, textNote: String) {
        CoroutineScope(IO).launch {
            val noteData = async { DB.addNote(Data(0,textTitle, textNote)) }.await()
            if (noteData !=null) {
                withContext(Main) {
                    val intent = Intent(this@AddNotes, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun NoteIsEmpty(textTitle: String) {
        CoroutineScope(IO).launch {
            val noteData = async { DB.addNote(Data(0,textTitle, "")) }.await()
            if (noteData !=null) {
                withContext(Main) {
                    val intent = Intent(this@AddNotes, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_to_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.BackMenu){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}