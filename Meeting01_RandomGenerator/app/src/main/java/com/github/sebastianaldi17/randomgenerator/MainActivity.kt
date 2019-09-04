package com.github.sebastianaldi17.randomgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.TextView

import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var resultText: TextView
    lateinit var rollButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.result_text)
        rollButton = findViewById(R.id.roll_button)
        rollButton.setOnClickListener {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
            flipCoin()
        }

    }

    private fun flipCoin() {
        val randomed: Int = Random.nextInt(1, 3)
        if(randomed == 1) {
            resultText.text = "Tails"
        } else {
            resultText.text = "Heads"
        }
    }
}
