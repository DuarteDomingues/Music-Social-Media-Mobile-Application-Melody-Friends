package com.example.projeto_dam

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private var userEmail: EditText? = null
    private var password: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null
    private var progressBar: ProgressBar? = null
    private var googleButton: ImageButton? = null
    private var fStore: FirebaseFirestore? = null

    //authentication
    private var mAuth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null

    private val RC_SIGN_IN: Int = 123
    private var email: String? = null
    private var pass: String? = null
    private var docUser: DocumentReference? = null




    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        fStore =  FirebaseFirestore.getInstance()
        var users = fStore!!.collection(FireStoreInfo.usersCollection)
        if (currentUser != null) {
         docUser = users!!.document(mAuth!!.currentUser!!.uid)



            docUser!!.get().addOnCompleteListener(OnCompleteListener { task ->

                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {

                        if ( document.getBoolean("gender") == null){
                            var intent = Intent(this@MainActivity, InstrumentsSelector::class.java)
                            startActivity(intent)



                        }
                        else{
                            //MUDAR
                            var intent = Intent(this@MainActivity, UserProfile::class.java)
                            startActivity(intent)
                        }
                        }
                }
                })
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fStore = FirebaseFirestore.getInstance()

        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.box_username)
        password = findViewById(R.id.box_password)
        loginButton = findViewById(R.id.btn_login)
        registerButton = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)
        googleButton = findViewById(R.id.log_google)
        googleValidation()


        handleLogin()
        handleRegisterButton()
        handleGoogleLogin()

    }

    private fun googleValidation(){

        // Configure Google Sign In
        //request to google
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleGoogleLogin(){

        googleButton!!.setOnClickListener{

            signIn()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            }

            catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    //user data has been recieved
    private fun firebaseAuthWithGoogle(idToken: String) {
        //credential
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth!!.currentUser

                    var users = fStore!!.collection(FireStoreInfo.usersCollection)
                    if (users!=null){

                        var email = user!!.email
                        var uId = user!!.uid
                        var indx = email!!.indexOf('@')
                        var userName = email.substring(0, indx)


                        val map = hashMapOf<String, Any>()
                        map.put("userName", userName!!)
                        map.put("email", email!!)
                        map.put("userId", uId!!)


                        var  documentReference: DocumentReference = fStore!!.collection(FireStoreInfo.usersCollection).document(uId!!)
                        documentReference.set(map).addOnSuccessListener (this){

                            if (task.isSuccessful){

                                Log.d("SUCESSO","SUCESSO")

                                val intent = Intent(this@MainActivity, InstrumentsSelector::class.java)
                                startActivity(intent)

                            }

                        }

                    }
                    else{

                        val intent = Intent(this@MainActivity, UserProfile::class.java)
                        startActivity(intent)
                    }
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }




    private fun handleLogin() {

        loginButton!!.setOnClickListener {

            if (loginValidation()){

                progressBar!!.visibility=VISIBLE
                authenticateUser()

            }
        }

    }


    private fun authenticateUser(){

        mAuth!!.signInWithEmailAndPassword(email!!,pass!!).addOnCompleteListener(this){

                task ->

            if (task.isSuccessful){
                Log.d("SUCESSOLOGIN","SUCESSOLOGIN")
                progressBar!!.visibility=INVISIBLE

                val intent = Intent(this@MainActivity, UserProfile::class.java)

                startActivity(intent)

            }
            else{

                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT ).show()
                progressBar!!.visibility=INVISIBLE

            }

        }
    }




    private fun loginValidation(): Boolean {

         email = userEmail!!.text.toString().trim()
         pass = password!!.text.toString().trim()

        if (email!!.isEmpty()) {

            userEmail!!.setError("Email is required")
            userEmail!!.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            userEmail!!.setError("Please provide a valid email")
            userEmail!!.requestFocus()
            return false

        }

        if (pass!!.isEmpty()) {

            password!!.setError("Password is required")
            password!!.requestFocus()
            return false
        }

        if (pass!!.length < 6) {

            password!!.setError("Minimum password length is 6 characters")
            password!!.requestFocus()
            return false
        }
        return true

    }




    private fun handleRegisterButton(){

        registerButton!!.setOnClickListener {

            val intent = Intent(this@MainActivity, Register::class.java)
            startActivity(intent)

        }

    }






}