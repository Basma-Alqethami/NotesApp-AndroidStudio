package com.example.notesapp.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.Room.Data
import com.example.notesapp.Room.NotesDao
import com.example.notesapp.Room.NotesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application): AndroidViewModel(application) {

    private val noteDao: NotesDao
    private val tasks: LiveData<List<Data>>

    init {
        noteDao = NotesDatabase.getDatabase(application).note_Dao()
        tasks = noteDao.getAllNotes()
    }

    fun getNotes(): LiveData<List<Data>> {
        return tasks
    }

    fun update(note: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.updateNote(note)
        }
    }

    fun delete(note: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteNote(note)
        }
    }

    fun add(note: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.addNote(note)
        }
    }
}