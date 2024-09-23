package com.example.autonomo4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    private lateinit var usuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var correo: EditText
    private lateinit var iniciar: Button
    private lateinit var crear: Button
    private lateinit var recordarme: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usuario = findViewById(R.id.usuario)
        contrasena = findViewById(R.id.contrasena)
        correo = findViewById(R.id.correo)
        iniciar = findViewById(R.id.iniciar)
        crear = findViewById(R.id.crear)
        recordarme = findViewById(R.id.recordar)

        crear.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        iniciar.setOnClickListener {
            savedata()
        }

        recordarme.setOnClickListener {
            loadata()
        }
    }

    private fun savedata() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val usuarioText = usuario.text.toString()
        val contrasenaText = contrasena.text.toString()
        val correoText = correo.text.toString()

        if (usuarioText.isEmpty() || contrasenaText.isEmpty() || correoText.isEmpty()) {
            Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        editor.putString("usuario", usuarioText)
        editor.putString("contrasena", contrasenaText)
        editor.putString("correo", correoText)

        editor.apply()
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
    }

    private fun loadata() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)

        val usuarioText = sharedPreferences.getString("usuario", "")
        val contrasenaText = sharedPreferences.getString("contrasena", "")
        val correoText = sharedPreferences.getString("correo", "")

        usuario.setText(usuarioText)
        contrasena.setText(contrasenaText)
        correo.setText(correoText)

        Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show()
    }
}