package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var rvAdapter: RVAdapter
    private lateinit var recyclerView : RecyclerView

    private var list: List<Data> = listOf()
    private val DB by lazy { NotesDatabase.getDatabase(this).note_Dao() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMain)
        GetAllData()
    }


    fun GetAllData() {
        CoroutineScope(IO).launch {
            val data = async { DB.getAllNotes() }.await()
            if (data !=null) {
                Log.d("nnnnnn","$data")
                withContext(Main) {
                    list = data
                    Log.d("nnnnnn","$list")
                    RVUpdate()
                }
            }
        }
    }

    fun RVUpdate() {
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