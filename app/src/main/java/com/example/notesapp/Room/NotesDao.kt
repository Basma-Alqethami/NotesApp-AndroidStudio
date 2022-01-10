package com.example.notesapp.Room


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.Room.Data

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Data)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Data>>

    @Update
    suspend fun updateNote(note: Data)

    @Delete
    suspend fun deleteNote(note: Data)
}