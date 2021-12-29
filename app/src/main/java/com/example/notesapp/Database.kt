package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class Database (context: Context): SQLiteOpenHelper(context,"notes.db", null, 1) {

    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE notes_table (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT NOT NULL, Note TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS notes_table")
        onCreate(db)
    }

    fun saveData(title: String, note: String){
        val contentValues = ContentValues()
        contentValues.put("Title", title)
        contentValues.put("Note", note)
        sqLiteDatabase.insert("notes_table", null, contentValues)
    }

    fun readData(): ArrayList<Data>{
        val allNotes = ArrayList<Data>()

        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes_table", null)

        if(cursor.count < 1){
            println("No Data Found")
        }else{
            while(cursor.moveToNext()){
                val id = cursor.getInt(0)
                val title = cursor.getString(1)
                val note = cursor.getString(2)
                allNotes.add(Data(id, title, note))
            }
        }
        return allNotes
    }

    fun updateData(data: Data){
        val contentValues = ContentValues()
        contentValues.put("Title", data.title)
        contentValues.put("Note", data.note)
        sqLiteDatabase.update("notes_table", contentValues, "Id = ${data.id}", null)
    }

    fun deleteData(data: Data){
        sqLiteDatabase.delete("notes_table", "Id = ${data.id}", null)
    }
}