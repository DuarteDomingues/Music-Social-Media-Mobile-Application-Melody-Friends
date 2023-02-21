package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class Register: AppCompatActivity()  {

    private var mAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null

    private var usernameField: EditText? = null
    private var passwordField: EditText? = null
    private var emailField: EditText? = null
    private var registerButton: Button? = null


    private var username: String? = null
    private var email: String? = null
    private var password: String? = null

    private var userId: String? = null
    private var progressBar: ProgressBar? = null


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()


        usernameField = findViewById(R.id.register_username)
        emailField = findViewById(R.id.register_email)
        passwordField = findViewById(R.id.register_password)
        registerButton = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBarId)



        registerButton!!.setOnClickListener {
            var isValidated= userValidation()
            if (isValidated){

                addUserDatabase()
            }
        }

    }

    private fun userValidation(): Boolean{

         username = usernameField!!.text.toString().trim()
         email = emailField!!.text.toString().trim()
         password = passwordField!!.text.toString().trim()

        if (username!!.isEmpty()){

            usernameField!!.setError("Username is required")
            usernameField!!.requestFocus()
            return false
        }

        if (email!!.isEmpty()){

            emailField!!.setError("Email is required")
            emailField!!.requestFocus()
            return false
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            emailField!!.setError("Please provide a valid email")
            emailField!!.requestFocus()
            return false

        }

        if (password!!.isEmpty()){

            passwordField!!.setError("Password is required")
            passwordField!!.requestFocus()
            return false
        }
        if (password!!.length < 6){

            passwordField!!.setError("Minimum password length is 6 characters")
            passwordField!!.requestFocus()
            return false
        }

        return true

    }

    private fun addUserDatabase(){

        progressBar!!.visibility= View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                userId = mAuth!!.currentUser!!.uid
               // var user = User(username!!,email!!)
                val map = hashMapOf<String, Any>()
                map.put("userName", username!!)
                map.put("email", email!!)
                map.put("userId", userId!!)


                var  documentReference: DocumentReference = fStore!!.collection(FireStoreInfo.usersCollection).document(userId!!)
                documentReference.set(map).addOnSuccessListener (this){

                    if (task.isSuccessful){

                        Log.d("SUCESSO","SUCESSO")
                        progressBar!!.visibility= View.INVISIBLE

                        val intent = Intent(this, InstrumentsSelector::class.java)
                        startActivity(intent)

                    }

                }

            }
            else{
                Log.d("userNotCreated","nope")
                Toast.makeText(this, "Error creating user",Toast.LENGTH_SHORT ).show()
                progressBar!!.visibility= View.INVISIBLE
            }


        }


    }



}