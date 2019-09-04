package com.github.sebastianaldi17.meeting02d_lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val to2 = findViewById<Button>(R.id.to2)

        Log.d("Test", "In onCreate 1")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        to2.setOnClickListener {
            val i = Intent(this, SecondActivity::class.java)
            startActivity(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Test", "In onDestroy 1")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Test", "In onStop 1")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Test", "In onRestart 1")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Test", "In onStop 1")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Test", "In onPause 1")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Test", "In onResume 1")
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
