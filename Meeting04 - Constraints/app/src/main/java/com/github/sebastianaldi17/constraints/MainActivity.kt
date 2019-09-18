package com.github.sebastianaldi17.constraints


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.sebastianaldi17.constraints.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.constraintLayout.setBackgroundColor(Color.parseColor("#8a8a8a"))
        restartGame()
    }

    private fun setOnClick(view: View) {
        binding.apply {
            if(view.id == selectedID) {
                // set to big star
                // view.setBackgroundColor(Color.parseColor("#ad2828"))
                view.setBackgroundResource(android.R.drawable.btn_star_big_on)
                val builder = AlertDialog.Builder(view.context)
                builder.setTitle("You win!")
                builder.setMessage("Press the star again to restart the game.")
                builder.setPositiveButton("Ok"){dialogInterface, which ->
                    Toast.makeText(view.context, "Press the star to restart the game.", Toast.LENGTH_LONG).show()
                }
                builder.create().show()
                view.setOnClickListener{
                    restartGame()
                }

            } else {
                // set to red x
                //view.setBackgroundColor(Color.parseColor("#000000"))
                view.setBackgroundResource(android.R.drawable.ic_delete)
            }
        }
    }
    private fun restartGame() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val boxIDList: List<Int> = listOf(
                binding.boxOneText.id,
                binding.boxTwoText.id,
                binding.boxThreeText.id,
                binding.boxFourText.id,
                binding.boxFiveText.id,
                binding.boxSixText.id
        )
        selectedID = boxIDList[Random.nextInt(0,boxIDList.size)]
        binding.apply {
            val boxOneText = boxOneText
            val boxTwoText = boxTwoText
            val boxThreeText = boxThreeText
            val boxFourText = boxFourText
            val boxFiveText = boxFiveText
            val boxSixText = boxSixText

            val views: List<View> = listOf(boxOneText, boxTwoText, boxThreeText, boxFourText, boxFiveText, boxSixText)

            for (view in views) {
                view.setOnClickListener{
                    setOnClick(it)
                }
            }
        }
        binding.constraintLayout.setBackgroundColor(Color.parseColor("#8a8a8a"))
    }
}
