package com.example.notesapp


import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Data)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): List<Data>

    @Update
    suspend fun updateNote(note: Data)

    @Delete
    suspend fun deleteNote(note: Data)
}