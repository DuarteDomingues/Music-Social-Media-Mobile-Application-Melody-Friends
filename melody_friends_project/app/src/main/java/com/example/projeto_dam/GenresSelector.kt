package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class GenresSelector: AppCompatActivity() {

    private var nextButton: ImageButton? = null
    private var instrumentsArr: ArrayList<String>? = null
    private var countryButton: Button? = null
    private var edmButton: Button? = null
    private var hiphopButton: Button? = null
    private var indieButton: Button? = null
    private var jazzButton: Button? = null
    private var popButton: Button? = null
    private var rockButton: Button? = null
    private var bluesButton: Button? = null
    private var sambaButton: Button? = null
    private var fadoButton: Button? = null
    private var raggaeButton: Button? = null
    private var dubstepButton: Button? = null
    private var mapGenres: HashMap<Int,Boolean>? = null
    private var genresArr: ArrayList<String>? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_genres)
        nextButton = findViewById(R.id.nextGenres)
        instrumentsArr = ArrayList<String>()

        countryButton = findViewById(R.id.selectCountry)
        edmButton = findViewById(R.id.selectEdm)
        hiphopButton = findViewById(R.id.selectHiphop)
        indieButton = findViewById(R.id.selectIndie)
        jazzButton = findViewById(R.id.selectJazz)
        popButton = findViewById(R.id.selectPop)
        rockButton = findViewById(R.id.selectRock)
        bluesButton = findViewById(R.id.selectBlues)
        sambaButton = findViewById(R.id.selectSamba)
        fadoButton = findViewById(R.id.selectFado)
        raggaeButton = findViewById(R.id.selectRaggae)
        dubstepButton = findViewById(R.id.selectDubsptep)

        val extras = intent.extras
        if (extras != null){
            Log.d("recebiarray","recebiarray")
            instrumentsArr = intent.getStringArrayListExtra("arrIns")
        }

        genresArr = ArrayList<String>()

        mapGenres = hashMapOf<Int, Boolean>()
        mapGenres!!.put(0, false)
        mapGenres!!.put(1, false)
        mapGenres!!.put(2, false)
        mapGenres!!.put(3, false)
        mapGenres!!.put(4, false)
        mapGenres!!.put(5, false)
        mapGenres!!.put(6, false)
        mapGenres!!.put(7, false)
        mapGenres!!.put(8, false)
        mapGenres!!.put(9, false)
        mapGenres!!.put(10, false)
        mapGenres!!.put(11, false)

        countryButton!!.setOnClickListener(handleButtons(0))
        edmButton!!.setOnClickListener(handleButtons(1))
        hiphopButton!!.setOnClickListener(handleButtons(2))
        indieButton!!.setOnClickListener(handleButtons(3))
        jazzButton!!.setOnClickListener(handleButtons(4))
        popButton!!.setOnClickListener(handleButtons(5))
        rockButton!!.setOnClickListener(handleButtons(6))
        bluesButton!!.setOnClickListener(handleButtons(7))
        sambaButton!!.setOnClickListener(handleButtons(8))
        fadoButton!!.setOnClickListener(handleButtons(9))
        raggaeButton!!.setOnClickListener(handleButtons(10))
        dubstepButton!!.setOnClickListener(handleButtons(11))

        handleNextButton()

    }

    private fun getFinalButtons(){

        for ((k, v) in mapGenres!!) {
            println("$k = $v")
            if (v == true){

                if (k == 0){
                    genresArr!!.add("country")
                }

                else if (k==1){
                    genresArr!!.add("edm")
                }
                else if (k==2){
                    genresArr!!.add("hiphop")
                }
                else if (k==3){
                    genresArr!!.add("indie")
                }
                else if (k==4){
                    genresArr!!.add("jazz")
                }
                else if (k==5){
                    genresArr!!.add("pop")
                }
                else if (k==6){
                    genresArr!!.add("rock")
                }
                else if (k==7){
                    genresArr!!.add("blues")
                }
                else if (k==8){
                    genresArr!!.add("samba")
                }
                else if (k==9){
                    genresArr!!.add("fado")
                }
                else if (k==10){
                    genresArr!!.add("raggae")
                }
                else if (k==11){
                    genresArr!!.add("dubstep")
                }
            }
        }
    }




    private fun animateButton(btn : Button?){

        btn!!.setAlpha(0f);

        //Animate the alpha value to 1f and set duration as 1.5 secs.
        btn!!.animate().alpha(1f).setDuration(700);

    }

    fun handleButtons(numb: Int?): View.OnClickListener? {

        return object:  View.OnClickListener{

            override fun onClick(v: View?) {

                if (numb==0){
                    //Set button alpha to zero
                    animateButton(countryButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        countryButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)
                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        countryButton!!.setBackgroundResource(R.drawable.border_instrument_1)
                    }
                }

                if (numb==1){

                    animateButton(edmButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        edmButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        edmButton!!.setBackgroundResource(R.drawable.border_instrument_3)
                    }
                }


                if (numb==2){
                    animateButton(hiphopButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        hiphopButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        hiphopButton!!.setBackgroundResource(R.drawable.border_instrument_2)
                    }
                }

                if (numb==3){

                    animateButton(indieButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        indieButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)
                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        indieButton!!.setBackgroundResource(R.drawable.border_instrument_1)
                    }
                }

                if (numb==4){

                    animateButton(jazzButton!!)
                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        jazzButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)
                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        jazzButton!!.setBackgroundResource(R.drawable.border_instrument_3)
                    }
                }

                if (numb==5){
                    animateButton(popButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        popButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)
                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        popButton!!.setBackgroundResource(R.drawable.border_instrument_2)
                    }
                }
                if (numb==6){

                    animateButton(rockButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        rockButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        rockButton!!.setBackgroundResource(R.drawable.border_instrument_1)
                    }
                }
                if (numb==7){

                    animateButton(bluesButton!!)
                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        bluesButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)
                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        bluesButton!!.setBackgroundResource(R.drawable.border_instrument_3)
                    }
                }
                if (numb==8){
                    animateButton(sambaButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        sambaButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        sambaButton!!.setBackgroundResource(R.drawable.border_instrument_2)
                    }
                }
                if (numb==9){
                    animateButton(fadoButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        fadoButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        fadoButton!!.setBackgroundResource(R.drawable.border_instrument_1)
                    }
                }
                if (numb==10){
                    animateButton(raggaeButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        raggaeButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        raggaeButton!!.setBackgroundResource(R.drawable.border_instrument_3)
                    }
                }
                if (numb==11){
                    animateButton(dubstepButton!!)

                    if (mapGenres!!.get(numb) == false){
                        mapGenres!!.set(numb!!, true)
                        dubstepButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)

                    }
                    else if (mapGenres!!.get(numb) == true){
                        mapGenres!!.set(numb!!, false)
                        dubstepButton!!.setBackgroundResource(R.drawable.border_instrument_2)
                    }
                }
            }
        }
    }


    private fun handleNextButton(){

        nextButton!!.setOnClickListener{

            getFinalButtons()
            val intent = Intent(this, EditUserAgeGender::class.java)
            intent.putStringArrayListExtra("arrIns",instrumentsArr!!)
            intent.putStringArrayListExtra("arrGenres",genresArr!!)
            startActivity(intent)

        }
    }

}