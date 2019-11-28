package com.github.aldi_matius.reminderapp.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.aldi_matius.reminderapp.AddEditTaskActivity
import com.github.aldi_matius.reminderapp.MainActivity
import com.github.aldi_matius.reminderapp.R
import kotlinx.android.synthetic.main.activity_notif.*
import kotlin.random.Random

class NotifActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)

        val v = this.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val Titletext = findViewById<TextView>(R.id.Titletext)
        val Desctext = findViewById<TextView>(R.id.Desctext)
        val intent = intent.extras
        val title = intent.getString(AddEditTaskActivity.EXTRA_TITLE)
        val desc = intent.getString(AddEditTaskActivity.EXTRA_DESCRIPTION)
        Titletext.text = title
        Desctext.text = desc


        kill.setOnClickListener {
                v.cancel()
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
        }
    }
}