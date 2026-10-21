package com.example.spike_localdb.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        //create the table if it doesn't already exist
        db.execSQL(
            """
            CREATE TABLE notes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                text TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        //only executes if DATABASE_VERSION ever changes value
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }

    fun insertNote(text: String) {
        //open access to the DB
        val db = writableDatabase

        val values = ContentValues()

        values.put("text", text)

        /*insert new row into table, equivilant to:
        INSERT INTO notes (text)
        VALUES ('Buy milk');
         */
        db.insert(
            "notes",
            null,
            values
        )
    }

    fun getAllNotes(): List<String> {

        val notes = mutableListOf<String>()

        //opens access to DB
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM notes ORDER BY id DESC",
            null
        )

        //looping through each row of the query results
        while (cursor.moveToNext()) {

            notes.add(
                cursor.getString(
                    cursor.getColumnIndexOrThrow("text")
                )
            )
        }

        cursor.close()

        return notes
    }

    companion object {
        //Constants used in the constructor above
        private const val DATABASE_NAME =
            "notes.db"

        private const val DATABASE_VERSION =
            1
    }
}