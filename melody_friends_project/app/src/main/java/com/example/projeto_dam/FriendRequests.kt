package com.example.projeto_dam

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendRequests : AppCompatActivity() {


    //firestore
    private var fStore: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserId: String? = null
    private var users: CollectionReference? = null

    private var usersNames: ArrayList<String>? = null
    private var layUsers: ArrayList<LinearLayout>? = null
    private var profiles: ArrayList<CircleImageView>? = null
    private var layoutMain: LinearLayout? = null


    //bottom buttons
    private var addButton: ImageButton? = null
    private var homeButton: ImageButton? = null
    private var aboutButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var profileButton: ImageButton? = null
    private var otherUserId: String? = null
    private var docUser: DocumentReference? = null
    private var acceptBtn: ArrayList<Button>? = null
    private var declineBtn: ArrayList<Button>? = null
    private var userImg: ArrayList<CircleImageView>? = null
    private var request: ArrayList<LinearLayout>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_requests)

        acceptBtn = ArrayList()
        declineBtn = ArrayList()
        userImg = ArrayList()
        request = ArrayList()

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()
        currentUserId = mAuth!!.currentUser!!.uid

        users = fStore!!.collection(FireStoreInfo.usersCollection)


        layoutMain = findViewById(R.id.layoutMain);
        usersNames = ArrayList()
        layUsers = ArrayList()
        profiles = ArrayList()




        docUser = users!!.document(currentUserId!!)

        homeButton = findViewById(R.id.home)
        aboutButton = findViewById(R.id.logout)
        profileButton = findViewById(R.id.profileButton)

        homeButton!!.setOnClickListener {

            val intent = Intent(this, Publications::class.java)
            startActivity(intent)

        }

        aboutButton!!.setOnClickListener {

            val intent = Intent(this, About::class.java)
            startActivity(intent)

        }

        profileButton!!.setOnClickListener {

            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)

        }


        getUserInfoDb()
    }


    private fun createLayout(userX: String) {


        val lin = LinearLayout(this)
        val layMatch = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val dpPad10 = convertDpToPixel(10f, applicationContext).toInt()
        layMatch.setMargins(dpPad10, dpPad10, dpPad10, dpPad10)
        lin.orientation = LinearLayout.HORIZONTAL
        lin.setBackgroundResource(R.drawable.border_friends)
        lin.weightSum = 6f
        lin.layoutParams = layMatch

        //imagem perfil usuario

        //imagem perfil usuario
        val photo = CircleImageView(this)
        val dpPad55 = convertDpToPixel(55f, applicationContext).toInt()
        val layParams = LinearLayout.LayoutParams(0, dpPad55, 1f)
        layParams.gravity = Gravity.CENTER
        val dpPad2 = convertDpToPixel(2f, applicationContext).toInt()
        layParams.setMargins(dpPad10, dpPad2, 0, dpPad2)
        photo.setImageResource(R.drawable.default2)
        photo.layoutParams = layParams
        photo.borderColor = Color.BLACK
        photo.borderWidth = dpPad2

        //layout com nome e os butoes

        //layout com nome e os butoes
        val layLin = LinearLayout(this)
        val layParams2 = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 5f)
        layLin.orientation = LinearLayout.VERTICAL
        layLin.layoutParams = layParams2


        //texto nome user

        /*
                //texto nome user
                val name = TextView(this)
                val layParams3 = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layParams3.gravity = Gravity.CENTER
                layParams3.setMargins(0, 0, 0, dpPad10)
                name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                name.setText(userX)
                name.layoutParams = layParams3

    */
        //layout dos butoes

        //layout dos butoes
        val linLay2 = LinearLayout(this)
        val layParams4 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layParams4.gravity = LinearLayout.HORIZONTAL

        linLay2.weightSum = 6f
        linLay2.layoutParams = layParams4


        val btnAccept = Button(this)
        val layParams5 = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f)
        val dpPad5 = convertDpToPixel(5f, applicationContext).toInt()
        layParams5.setMargins(dpPad5, 0, dpPad5, dpPad5)

        val name = TextView(this)
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        name.setText(userX)
        name.layoutParams = layParams5

        name.setTextColor(Color.WHITE)
        btnAccept.setMinimumHeight(0)
        btnAccept.setMinHeight(0)
        btnAccept.setText("accept")
        btnAccept.setTextColor(Color.WHITE)
        btnAccept.setLayoutParams(layParams5)
        btnAccept.setTextColor(Color.WHITE)
        btnAccept.setBackgroundResource(R.drawable.border_acccept)


        val btnDecline = Button(this)
        btnDecline.setText("decline")
        btnDecline.setTextColor(Color.rgb(154, 198, 197))
        btnDecline.setMinimumHeight(0)
        btnDecline.setMinHeight(0)
        btnDecline.setLayoutParams(layParams5)
        btnDecline.setBackgroundResource(R.drawable.box_around)

        linLay2.addView(name)
        linLay2.addView(btnAccept)
        linLay2.addView(btnDecline)


        // layLin.addView(name)
        layLin.addView(linLay2)

        lin.addView(photo)
        lin.addView(layLin)

        acceptBtn!!.add(btnAccept)
        declineBtn!!.add(btnDecline)
        userImg!!.add(photo)
        request!!.add(lin)
        layoutMain!!.addView(lin)


    }


    private fun getUserInfoDb() {
//docUser

        docUser!!.get().addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                var usersArr = ArrayList<String>()
                val document = task.result
                if (document != null) {
                    if (document.get("friendRequests") != null) {
                        usersArr = document.get("friendRequests") as ArrayList<String>


                        for (user in usersArr) {
                            var c =0;

                            var docUserRequest: DocumentReference? = users!!.document(user!!)

                            docUserRequest!!.get()
                                .addOnCompleteListener(OnCompleteListener { task ->

                             if (task.isSuccessful) {
                                 val doc = task.result

                                  if (doc != null) {

                                      var  name = doc.getString("userName")!!
                                      var userId = doc.getString("userId")

                                      var userImage = ""
                                      if (doc.getString("userImage") != null) {
                                          userImage = doc.getString("userImage")!!

                                      }
                                      createLayout(name)

                                      if (userImage != ""){

                                          Picasso.with(this).load(userImage).fit().centerInside().into(userImg!!.get(c))

                                      }
                                      c++




                                  }

                                 addEmptySpace()



                                    }


                                })


                        }


                    }
                }
            }

        })


    }


    private fun addEmptySpace() {
        val dpPad56 = convertDpToPixel(56f, applicationContext).toInt()
        val commentBtn = ImageButton(this)
        commentBtn.setBackgroundColor(Color.TRANSPARENT)
        val layImg2 = LinearLayout.LayoutParams(dpPad56, dpPad56)
        commentBtn.layoutParams = layImg2
        layoutMain!!.addView(commentBtn)
    }


    private fun arrToString(arr: ArrayList<String>): String {

        var strFinal = ""
        val c = 0
        for (i in arr) {
            if (strFinal.equals("")) {
                strFinal += i
            } else if (!strFinal.equals("")) {
                strFinal = strFinal + ", " + i!!
            }
        }
        return strFinal

    }


    private fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.getResources()
            .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }


}