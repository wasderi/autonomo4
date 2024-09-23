package com.example.autonomo4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var usuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var correo: EditText
    private lateinit var registrar: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        dbHelper = DatabaseHelper(this)

        usuario = findViewById(R.id.usuario)
        contrasena = findViewById(R.id.contrasena)
        correo = findViewById(R.id.correo)
        registrar = findViewById(R.id.registrar)

        registrar.setOnClickListener {
            val usuarioText = usuario.text.toString()
            val contrasenaText = contrasena.text.toString()
            val correoText = correo.text.toString()

            if (usuarioText.isEmpty() || contrasenaText.isEmpty() || correoText.isEmpty()) {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val result = dbHelper.insertUser(usuarioText, contrasenaText, correoText)
                if (result) {
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}