package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class Database (context: Context): SQLiteOpenHelper(context,"notes.db", null, 1) {

    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table notes_table (Id integer primary key autoincrement, Title text not null, Note text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists notes_table")
        onCreate(db)
    }

    fun saveData(title: String, note: String){
        val contentValues = ContentValues()
        contentValues.put("Title", title)
        contentValues.put("Note", note)
        sqLiteDatabase.insert("notes_table", null, contentValues)
    }

    fun readData(): ArrayList<Data>{
        val people = arrayListOf<Data>()

        // Read all data using cursor
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes_table", null)

        if(cursor.count < 1){  // Handle empty table
            println("No Data Found")
        }else{
            while(cursor.moveToNext()){  // Iterate through table and populate people Array List
                val pk = cursor.getInt(0)  // The integer value refers to the column
                val name = cursor.getString(1)
                val location = cursor.getString(2)
                people.add(Data(pk, name, location))
            }
        }
        return people
    }
}