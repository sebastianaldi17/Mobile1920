package com.github.sebastianaldi17.meeting03_lorengenerator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.github.sebastianaldi17.meeting03_lorengenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val myData: MyData = MyData("Loren Ipsum")

    override fun onCreate(savedInstanceState: Bundle?) {
        val LorenIpsum: MyData = MyData("Loren Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "        Duis eget leo nec mi imperdiet fermentum.\n" +
                "        Ut neque lectus, egestas et faucibus nec, lobortis maximus neque.\n" +
                "        Integer auctor nisi gravida accumsan laoreet.\n" +
                "        Cras molestie hendrerit massa et finibus.\n" +
                "        Sed auctor nibh odio, nec pellentesque ex finibus sit amet.\n" +
                "        Quisque tempus, enim fringilla maximus rutrum,\n" +
                "        lacus libero facilisis est, non fermentum eros ex nec diam.\n" +
                "        Donec id interdum ligula, ut convallis orci.")
        val BaconIpsum: MyData = MyData("Bacon Ipsum", "Bacon ipsum dolor amet tenderloin leberkas spare ribs porchetta,\n" +
                "        biltong venison pig pork loin.\n" +
                "        Pig drumstick sausage short loin jerky.\n" +
                "        Strip steak chicken salami,\n" +
                "        doner bresaola swine cow tri-tip\n" +
                "        leberkas t-bone shoulder ball tip shankle filet mignon.\n" +
                "        Corned beef capicola tri-tip,\n" +
                "        chuck beef ribs jerky turducken buffalo\n" +
                "        ground round biltong pork belly venison.\n" +
                "        Tri-tip doner short loin,\n" +
                "        turducken meatloaf ball tip prosciutto bacon.")
        val SpaceIpsum: MyData = MyData("Space Ipsum", "Space, the final frontier.\n" +
                "        These are the voyages of the Starship Enterprise.\n" +
                "        Its five-year mission: to explore strange new worlds,\n" +
                "        to seek out new life and new civilizations,\n" +
                "        to boldly go where no man has gone before.\n" +
                "        Many say exploration is part of our destiny,\n" +
                "        but it’s actually our duty to future generations\n" +
                "        and their quest to ensure the survival of the human species.")
        val SaganIpsum: MyData = MyData("Sagan Ipsum", "Flatland hydrogen atoms a billion trillion explorations invent the universe\n" +
                "        bits of Star stuff harvesting star light shores of\n" +
                "        the cosmic ocean inconspicuous motes of rock and gas\n" +
                "        vastness is bearable only through love\n" +
                "        extraordinary claims require extraordinary evidence laws of physics.\n" +
                "        The carbon in our apple pies from which we spring rich in heavy atoms\n" +
                "        the only home we\\'ve ever known extraordinary claims require extraordinary evidence\n" +
                "        stirred by starlight and billions upon billions\n" +
                "        upon billions upon billions upon billions upon billions upon billions.")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.myData = myData
        binding.creatorButton.setOnClickListener{
            pickCreatorName(it)
        }
        binding.creatorText.setOnClickListener{
            changeCreatorName(it)
        }
        val spinner: Spinner = findViewById(R.id.ipsum_spinner)

        ArrayAdapter.createFromResource(this, R.array.ipsum_array, android.R.layout.simple_spinner_item)
            .also { adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selected = spinner.selectedItem.toString()
                if(selected.equals("Lorem")) {
                    binding.textView2.text = LorenIpsum.creator
                } else if(selected.equals("Bacon")) {
                    binding.textView2.text = BaconIpsum.creator
                } else if(selected.equals("Space")) {
                    binding.textView2.text = SpaceIpsum.creator
                } else if(selected.equals("Sagan")) {
                    binding.textView2.text = SaganIpsum.creator
                }
            }
        }
    }
    private fun changeCreatorName(view: View) {
        binding.apply{
            view.visibility = GONE
            creatorFill.visibility = View.VISIBLE
            creatorButton.visibility = View.VISIBLE
            creatorFill.requestFocus()
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.creatorFill, 0)
    }
    private fun pickCreatorName(view: View) {
        binding.apply{
            myData?.creator  = creatorFill.text.toString()
            creatorText.text = binding.creatorFill.text.toString()
            creatorFill.visibility = GONE
            creatorButton.visibility = GONE
            creatorText.visibility = View.VISIBLE
        }

        view.visibility = GONE

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
