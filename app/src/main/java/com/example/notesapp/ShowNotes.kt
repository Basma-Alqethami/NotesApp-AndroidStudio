package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.*

class ShowNotes : AppCompatActivity() {

    private lateinit var et_Title: EditText
    private lateinit var et_Note: EditText
    private lateinit var btnUpdate: Button
    private val DB by lazy { NotesDatabase.getDatabase(this).note_Dao() }

    private var id = 0

    var title11 = ""
    var note11 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_notes)

        et_Title = findViewById(R.id.et_Title)
        et_Note = findViewById(R.id.et_Note)
        btnUpdate = findViewById(R.id.btnUpdate)

        val disData = intent.getSerializableExtra("displayData") as Data
        id = disData.id
        et_Title.setText(disData.title)
        et_Note.setText(disData.note)

        btnUpdate.setOnClickListener {

            title11 = et_Title.text.toString()
            note11 = et_Note.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val noteData = async { DB.updateNote(Data(id, title11, note11)) }.await()
                if (noteData != null) {
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@ShowNotes, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
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

                CoroutineScope(Dispatchers.IO).launch {
                    val noteData = async { DB.deleteNote(Data(id, title11, note11)) }.await()
                    if (noteData != null) {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@ShowNotes, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}