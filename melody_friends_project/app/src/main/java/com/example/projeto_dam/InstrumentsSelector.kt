package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class InstrumentsSelector: AppCompatActivity() {

    private var nextButton: ImageButton? = null
    private var guitarButton: Button? = null
    private var drumsButton: Button? = null
    private var flueButton: Button? = null
    private var pianoButton: Button? = null
    private var bassButton: Button? = null
    private var saxButton: Button? = null
    private var violinButton: Button? = null
    private var singerButton: Button? = null
    private var trumpetButton: Button? = null
    private var harpButton: Button? = null
    private var accordionButton: Button? = null
    private var clarinetButton: Button? = null
    private var mapInstruments: HashMap<Int,Boolean>? = null
    private var instrumentsArr: ArrayList<String>? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_instruments)
        nextButton = findViewById(R.id.nextInstruments)

        guitarButton = findViewById(R.id.selectGuitar)
        drumsButton = findViewById(R.id.selectDrums)
        flueButton = findViewById(R.id.selectFlute)
        pianoButton = findViewById(R.id.selectPiano)
        bassButton = findViewById(R.id.selectBass)
        saxButton = findViewById(R.id.selectSax)
        violinButton = findViewById(R.id.selectViolin)
        singerButton = findViewById(R.id.selectSinger)
        trumpetButton = findViewById(R.id.selectTrumpet)
        harpButton = findViewById(R.id.selectHarp)
        accordionButton = findViewById(R.id.selectAccordion)
        clarinetButton = findViewById(R.id.selectClarinet)

        //handleButtons()

        instrumentsArr = ArrayList<String>()

         mapInstruments = hashMapOf<Int, Boolean>()
        mapInstruments!!.put(0, false)
        mapInstruments!!.put(1, false)
        mapInstruments!!.put(2, false)
        mapInstruments!!.put(3, false)
        mapInstruments!!.put(4, false)
        mapInstruments!!.put(5, false)
        mapInstruments!!.put(6, false)
        mapInstruments!!.put(7, false)
        mapInstruments!!.put(8, false)
        mapInstruments!!.put(9, false)
        mapInstruments!!.put(10, false)
        mapInstruments!!.put(11, false)



        guitarButton!!.setOnClickListener(handleButtons(0))
        drumsButton!!.setOnClickListener(handleButtons(1))
        flueButton!!.setOnClickListener(handleButtons(2))
        pianoButton!!.setOnClickListener(handleButtons(3))
        bassButton!!.setOnClickListener(handleButtons(4))
        saxButton!!.setOnClickListener(handleButtons(5))
        violinButton!!.setOnClickListener(handleButtons(6))
        singerButton!!.setOnClickListener(handleButtons(7))
        trumpetButton!!.setOnClickListener(handleButtons(8))
        harpButton!!.setOnClickListener(handleButtons(9))
        accordionButton!!.setOnClickListener(handleButtons(10))
        clarinetButton!!.setOnClickListener(handleButtons(11))


        handleNextButton()


    }

    private fun getFinalButtons(){

        for ((k, v) in mapInstruments!!) {
            println("$k = $v")
            if (v == true){

                if (k == 0){
                    instrumentsArr!!.add("guitar")
                }

                else if (k==1){
                    instrumentsArr!!.add("drums")
                }
                else if (k==2){
                    instrumentsArr!!.add("flute")
                }
                else if (k==3){
                    instrumentsArr!!.add("piano")
                }
                else if (k==4){
                    instrumentsArr!!.add("bass")
                }
                else if (k==5){
                    instrumentsArr!!.add("sax")
                }
                else if (k==6){
                    instrumentsArr!!.add("violin")
                }
                else if (k==7){
                    instrumentsArr!!.add("singer")
                }
                else if (k==8){
                    instrumentsArr!!.add("trumpet")
                }
                else if (k==9){
                    instrumentsArr!!.add("harp")
                }
                else if (k==10){
                    instrumentsArr!!.add("accordion")
                }
                else if (k==11){
                    instrumentsArr!!.add("clarinet")
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
                    animateButton(guitarButton!!)

                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        guitarButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        guitarButton!!.setBackgroundResource(R.drawable.border_instrument_1)

                    }

                }

                if (numb==1){

                    animateButton(drumsButton!!)



                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        drumsButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        drumsButton!!.setBackgroundResource(R.drawable.border_instrument_3)

                    }

                }


                if (numb==2){

                    animateButton(flueButton!!)


                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        flueButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        flueButton!!.setBackgroundResource(R.drawable.border_instrument_2)

                    }

                }


                if (numb==3){

                    animateButton(pianoButton!!)


                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        pianoButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        pianoButton!!.setBackgroundResource(R.drawable.border_instrument_1)

                    }

                }

                if (numb==4){

                    animateButton(bassButton!!)
                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        bassButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        bassButton!!.setBackgroundResource(R.drawable.border_instrument_3)

                    }

                }

                if (numb==5){
                    animateButton(saxButton!!)

                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        saxButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        saxButton!!.setBackgroundResource(R.drawable.border_instrument_2)

                    }

                }
                if (numb==6){

                    animateButton(violinButton!!)

                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        violinButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        violinButton!!.setBackgroundResource(R.drawable.border_instrument_1)

                    }

                }
                if (numb==7){

                    animateButton(singerButton!!)
                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        singerButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        singerButton!!.setBackgroundResource(R.drawable.border_instrument_3)

                    }

                }
                if (numb==8){
                    animateButton(trumpetButton!!)



                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        trumpetButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        trumpetButton!!.setBackgroundResource(R.drawable.border_instrument_2)

                    }

                }
                if (numb==9){
                    animateButton(harpButton!!)

                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        harpButton!!.setBackgroundResource(R.drawable.border_instrument_1_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        harpButton!!.setBackgroundResource(R.drawable.border_instrument_1)

                    }

                }
                if (numb==10){
                    animateButton(accordionButton!!)

                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        accordionButton!!.setBackgroundResource(R.drawable.border_instrument_3_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        accordionButton!!.setBackgroundResource(R.drawable.border_instrument_3)

                    }

                }
                if (numb==11){
                    animateButton(clarinetButton!!)

                    if (mapInstruments!!.get(numb) == false){
                        mapInstruments!!.set(numb!!, true)
                        clarinetButton!!.setBackgroundResource(R.drawable.border_instrument_2_active)


                    }
                    else if (mapInstruments!!.get(numb) == true){
                        mapInstruments!!.set(numb!!, false)
                        clarinetButton!!.setBackgroundResource(R.drawable.border_instrument_2)

                    }

                }
            }


        }




    }



    private fun handleNextButton(){

        nextButton!!.setOnClickListener{


            getFinalButtons()

            val intent = Intent(this, GenresSelector::class.java)
            intent.putStringArrayListExtra("arrIns",instrumentsArr!!)

            startActivity(intent)

        }
    }




}