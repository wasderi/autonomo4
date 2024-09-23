package com.example.autonomo4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "usuarios"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "usuario TEXT, "
                + "contrasena TEXT, "
                + "correo TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(usuario: String, contrasena: String, correo: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("usuario", usuario)
        contentValues.put("contrasena", contrasena)
        contentValues.put("correo", correo)

        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }

    fun checkUser(usuario: String, contrasena: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE usuario=? AND contrasena=?", arrayOf(usuario, contrasena))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun getLastUser(): Triple<String, String, String>? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT 1", null)

        return if (cursor.moveToFirst()) {
            val usuario = cursor.getString(cursor.getColumnIndex("usuario"))
            val contrasena = cursor.getString(cursor.getColumnIndex("contrasena"))
            val correo = cursor.getString(cursor.getColumnIndex("correo"))
            cursor.close()
            Triple(usuario, contrasena, correo)
        } else {
            cursor.close()
            null
        }
    }
}