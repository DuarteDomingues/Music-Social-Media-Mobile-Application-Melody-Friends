package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CreateContent : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var currentUserId: String? = null
    private var currDate: String? = null
    private var pubTextView: EditText? = null
    private var locationTextView: EditText? = null
    private var submitButton: Button? = null
    private var titleTextView: EditText? = null
    private var users: CollectionReference? = null
    private var docUser: DocumentReference? = null
    public var name: String = ""

    private var homeButton: ImageButton? = null
    private var aboutButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var friendButton: ImageButton? = null
    private var profileButton: ImageButton? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post)
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()
        currentUserId = mAuth!!.currentUser!!.uid

        users = fStore!!.collection(FireStoreInfo.usersCollection)
        docUser = users!!.document(currentUserId!!)

        homeButton = findViewById(R.id.home)
        aboutButton = findViewById(R.id.logout)
        profileButton = findViewById(R.id.profileButton)

        homeButton!!.setOnClickListener{

            val intent = Intent(this, Publications::class.java)
            startActivity(intent)

        }

        aboutButton!!.setOnClickListener{

            val intent = Intent(this, About::class.java)
            startActivity(intent)

        }

        profileButton!!.setOnClickListener{

            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)

        }



        pubTextView = findViewById(R.id.createPub)
        locationTextView = findViewById(R.id.createLocation)
        submitButton = findViewById(R.id.btn_submit_create)
        titleTextView = findViewById(R.id.createTitle)
        getUserName()
        handleSubmitButton()

    }


    private fun publicationDate() {

        val c: Date = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        currDate = df.format(c)


    }

    private fun publicationText(): String {

        var text = pubTextView!!.text.toString()
        return text!!

    }

    private fun locationText(): String {

        var text = locationTextView!!.text.toString()
        return text!!

    }

    private fun titleView(): String {

        var text = titleTextView!!.text.toString()
        return text!!

    }

    private fun getUserName(){
        docUser!!.get().addOnCompleteListener(OnCompleteListener {

                task ->
            if (task.isSuccessful) {

                val document = task.result
                if (document != null) {

                    name = document.getString("userName")!!

                }
            }
        })
    }


    private fun addPublicationToDatabase() {

        publicationDate()

        var likes = ArrayList<String>()


        var pubText = publicationText()
        var locationText = locationText()
        var title = titleView()
        var timeMillis = System.currentTimeMillis()
        val map = hashMapOf<String, Any>()
        map.put("title", title)
        map.put("pubDate", currDate!!)
        map.put("text", pubText!!)
        map.put("location", locationText)
        map.put("timeMilis", timeMillis)
        map.put("likes", likes)
        map.put("userId", currentUserId!!)
        var xd = name!!
        map.put("userName", xd)

        var documentReference: DocumentReference =
            fStore!!.collection(FireStoreInfo.publicationCollection)
                .document(timeMillis.toString())
        documentReference.set(map).addOnSuccessListener(this) {

            val intent = Intent(this, Publications::class.java)

             startActivity(intent)


        }
    }


    private fun handleSubmitButton() {

        submitButton!!.setOnClickListener {

            addPublicationToDatabase()


        }
    }


}