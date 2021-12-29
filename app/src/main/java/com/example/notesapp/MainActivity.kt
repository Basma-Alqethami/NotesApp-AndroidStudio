package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvAdapter: RVAdapter
    private lateinit var recyclerView : RecyclerView

    private var list = ArrayList<Data>()
    private val DB by lazy { Database(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = DB.readData()

        recyclerView = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(list)
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.AddMenu){
            val intent = Intent(this, AddNotes::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}