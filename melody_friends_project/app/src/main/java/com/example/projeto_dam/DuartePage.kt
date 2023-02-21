package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DuartePage: AppCompatActivity() {

    private var backBtn: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        backBtn = findViewById(R.id.back)

        backBtn!!.setOnClickListener{


            val intent = Intent(this, About::class.java)
            startActivity(intent)



        }


    }
}