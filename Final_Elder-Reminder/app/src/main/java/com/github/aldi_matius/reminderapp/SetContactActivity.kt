package com.github.aldi_matius.reminderapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SetContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        val phone = findViewById<EditText>(R.id.phone_number)
        val savebtn = findViewById<Button>(R.id.savebtn)
        val cancelbtn = findViewById<Button>(R.id.cancelbtn)

        savebtn.setOnClickListener {
            if(phone.text.toString().matches(Regex("[0-9]+"))) {
                it.context.openFileOutput("phone.txt", Context.MODE_PRIVATE).use {
                    it.write(phone.text.toString().toByteArray())
                }
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(it.context, "Not a valid phone number!", Toast.LENGTH_SHORT).show()
            }
        }
        cancelbtn.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }
}