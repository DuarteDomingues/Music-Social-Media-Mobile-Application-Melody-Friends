package com.example.projeto_dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.squareup.picasso.Picasso

class UserProfile : AppCompatActivity() {

    private var fStore: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserId: String? = null
    private var users: CollectionReference? = null
    private var docUser: DocumentReference? = null
    private var buttonEdit: Button? = null
    private var email: String? = null

    //TEXT VIEWS
    private var userText: TextView? = null
    private var locationText: TextView? = null
    private var gender_age: TextView? = null
    private var favArtistsView: TextView? = null
    private var bioView: TextView? = null
    private var skillView: TextView? = null
    private var genresView: TextView? = null
    private var instrumentsView: TextView? = null
    private var bioFinal: String = ""
    private var favArtistsFinal: String = ""
    private var locationFinal: String = ""
    private var userImage: ImageView? = null

    //buttons de baixo
    private var addButton: ImageButton? = null
    private var homeButton: ImageButton? = null
    private var aboutButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var friendButton: ImageButton? = null

    private var otherUserId: String? = null
    private var isEdit: Boolean = true

    private var btn_friends: Button? = null

    private var premiumButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()
        currentUserId = mAuth!!.currentUser!!.uid

        users = fStore!!.collection(FireStoreInfo.usersCollection)

        buttonEdit = findViewById(R.id.btn_edit)
        userText = findViewById(R.id.userInfo)
        locationText = findViewById(R.id.userLoc)
        gender_age = findViewById(R.id.userAge)
        favArtistsView = findViewById(R.id.favArtists)
        bioView = findViewById(R.id.bio)
        skillView = findViewById(R.id.userSkill)
        genresView = findViewById(R.id.favoriteGenres)
        instrumentsView = findViewById(R.id.usertype)
        userImage = findViewById(R.id.profileImg)
        friendButton = findViewById(R.id.friendBtn)


        btn_friends = findViewById(R.id.btn_friends)

        addButton = findViewById(R.id.add)
        homeButton = findViewById(R.id.home)
        aboutButton = findViewById(R.id.logout)
        searchButton = findViewById(R.id.search)

        premiumButton = findViewById(R.id.Btn_premium)

        val extras = intent.extras
        if (extras != null) {
            otherUserId = intent.getStringExtra("otherUserId")

        }


        homeButton!!.setOnClickListener {

            val intent = Intent(this, Publications::class.java)
            startActivity(intent)

        }



        searchButton!!.setOnClickListener {

            val intent = Intent(this, SearchUsers::class.java)
            startActivity(intent)

        }


        aboutButton!!.setOnClickListener {

            val intent = Intent(this, About::class.java)
            startActivity(intent)

        }

        friendButton!!.setOnClickListener {

            val intent = Intent(this, FriendRequests::class.java)
            startActivity(intent)

        }

        var idUser = currentUserId!!

        if (otherUserId != null) {

            idUser = otherUserId!!
            // buttonEdit!!.visibility = GONE
            buttonEdit!!.setText("Send Friend Request")
            isEdit = false


        }

        btn_friends!!.setOnClickListener {

            val intent = Intent(this, Friends::class.java)
            intent.putExtra("currUserId",idUser)
            startActivity(intent)

        }

        premiumButton!!.setOnClickListener{

            Log.w("PREMIUM", "PREMIUM")



        }



        docUser = users!!.document(idUser)
        Log.d("DOCUSERCONA", idUser)
        setProfileImg()
        getUserInfoFromFirestore()
        handleButton()
        handleAddButton()

    }


    private fun setProfileImg() {

        docUser!!.get().addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    if (document.getString("userImage") != null) {

                        var userImg = document.getString("userImage")!!


                        Picasso.with(this).load(userImg).fit().centerInside().into(userImage)
                    }
                }
            }

        })

        // var str_img = "https://firebasestorage.googleapis.com/v0/b/melody-friends.appspot.com/o/profile%2Fimages385303785?alt=media&token=fa5edce4-e5a3-457e-b25d-198be3d4bd73"
        // Picasso.with(this).load(str_img).fit().centerInside().into(editImage);

    }


    fun getUserInfoFromFirestore() {

        docUser!!.get().addOnCompleteListener(OnCompleteListener {

                task ->
            if (task.isSuccessful) {
                var name = ""
                var bio = ""
                var location = ""
                var age: Long?
                age = 0
                var skillLevel = "Casual"
                var genderStr = "Male"
                var arrGenres = ArrayList<String>()
                var arrInstruments = ArrayList<String>()
                var favoriteArtists = ""

                val document = task.result
                if (document != null) {
                    email = document.getString("email")!!
                    name = document.getString("userName")!!


                    Log.w("EMAIL", email!!)
                    //Log.w("USERNAME",userName!!)

                    if (document.getString("bio") != null) {
                        bio = document.getString("bio")!!

                    }

                    if (document.getString("favoriteArtists") != null) {
                        favoriteArtists = document.getString("favoriteArtists")!!

                    }

                    if (document.get("genres") != null) {
                        arrGenres = document.get("genres") as ArrayList<String>

                    }

                    if (document.get("instruments") != null) {
                        arrInstruments = document.get("instruments") as ArrayList<String>

                    }

                    if (document.getString("location") != null) {
                        location = document.getString("location")!!

                    }
                    if (document.getLong("age") != null) {
                        age = document.getLong("age")!!

                    }

                    if (document.getString("skill") != null) {
                        skillLevel = document.getString("skill")!!
                    }
                    if (document.getBoolean("gender") != null) {

                        var gender = (document.getBoolean("gender"))

                        if (gender!!) {
                            genderStr = "Female"
                        }


                    }

                }
                var genres = getGenresArrStr(arrGenres!!)
                var instr = getGenresArrStr(arrInstruments!!)
                var ageStr = age.toString()
                updateUserFields(
                    name,
                    location,
                    bio,
                    ageStr,
                    skillLevel!!,
                    genderStr,
                    genres!!,
                    instr!!,
                    favoriteArtists!!
                )

                bioFinal = bio
                locationFinal = location
                favArtistsFinal = favoriteArtists

            }

        })


    }

    fun getGenresArrStr(arr: ArrayList<String>): String {
        var genres = ""
        for (i in arr) {
            genres = genres + " " + i
        }
        return genres
    }


    fun updateUserFields(
        name: String?,
        location: String?,
        bio: String?,
        age: String?,
        skillLevel: String?,
        genderStr: String?,
        genres: String?,
        instruments: String?,
        favArtists: String?
    ) {

        userText!!.setText(name)
        if (location != "") {
            locationText!!.setText(location)
        }
        if (bio != "") {
            bioView!!.setText(bio)
        }

        if (favArtists != "") {
            favArtistsView!!.setText(favArtists)

        }


        skillView!!.setText(skillLevel)
        var str_gender_age = age + ", " + genderStr!!
        gender_age!!.setText(str_gender_age)
        genresView!!.setText(genres)
        instrumentsView!!.setText(instruments)


    }

    fun handleButton() {

        buttonEdit!!.setOnClickListener {

            if (isEdit) {

                val intent = Intent(this, EditUserProfile::class.java)
                intent.putExtra("locIntent", locationFinal)
                intent.putExtra("bioIntent", bioFinal)
                intent.putExtra("favArtistsIntent", favArtistsFinal)

                startActivity(intent)
            } else {
                Log.w("ADD", "ADDFRIENDS")

                var docOtherUser: DocumentReference = users!!.document(otherUserId!!)

                docOtherUser!!.get().addOnCompleteListener(OnCompleteListener {

                        task ->
                    if (task.isSuccessful) {

                        var otherUserFriendsReq = ArrayList<String>()
                        val document = task.result
                        if (document != null) {


                            if (document.get("friendResquests") != null) {
                                otherUserFriendsReq =
                                    document.get("friendResquests") as ArrayList<String>

                            }

                            if (!otherUserFriendsReq.contains(currentUserId)) {
                                otherUserFriendsReq.add(currentUserId!!)

                                val map = hashMapOf<String, Any>()
                                map.put("friendRequests", otherUserFriendsReq)
                                var documentReference: DocumentReference =
                                    fStore!!.collection(FireStoreInfo.usersCollection)
                                        .document(otherUserId!!)
                                documentReference.set(map, SetOptions.merge())
                                    .addOnSuccessListener(this) {

                                        buttonEdit!!.setText("Friend Request sent")

                                    }

                            }
                        }

                    }
                })

            }
        }

    }







    private fun handleAddButton() {

        addButton!!.setOnClickListener {

            val intent = Intent(this, CreateContent::class.java)
            startActivity(intent)

        }

    }


}