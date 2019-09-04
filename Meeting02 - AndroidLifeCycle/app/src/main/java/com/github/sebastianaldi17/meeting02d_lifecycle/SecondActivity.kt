package com.github.sebastianaldi17.meeting02d_lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_second)
        val to1 = findViewById<Button>(R.id.to1)

        Log.d("Test", "In onCreate 2")

        to1.setOnClickListener {
            setContentView(R.layout.content_main)
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Test", "In onDestroy 2")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Test", "In onStop 2")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Test", "In onRestart 2")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Test", "In onStop 2")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Test", "In onPause 2")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Test", "In onResume 2")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
