package com.example.notesapp.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//data class Data (val id: Int, val title:String, val note: String) : Serializable

@Entity(tableName = "notes_table")
data class Data(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title:String,
    val note: String): Serializable