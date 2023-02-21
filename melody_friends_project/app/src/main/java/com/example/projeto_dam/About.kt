package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class About: AppCompatActivity()  {

    private var mAuth: FirebaseAuth? = null

    var aboutButton: Button? = null
    var logoutButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_menu)
        mAuth = FirebaseAuth.getInstance();

        aboutButton= findViewById(R.id.about)
        logoutButton = findViewById(R.id.logoutButton)
        aboutButton!!.setOnClickListener{

            val intent = Intent(this, DuartePage::class.java)
            startActivity(intent)

        }

        logoutButton!!.setOnClickListener{

            mAuth!!.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }



    }


}