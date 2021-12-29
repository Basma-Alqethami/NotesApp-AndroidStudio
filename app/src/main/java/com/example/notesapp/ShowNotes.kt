package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

class ShowNotes : AppCompatActivity() {

    private lateinit var et_Title: EditText
    private lateinit var et_Note: EditText
    private lateinit var btnUpdate: Button
    private var id = 0
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

        btnUpdate.setOnClickListener {  }
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