package com.example.projeto_dam

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class Friends: AppCompatActivity() {


    //firestore
    private var fStore: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserId: String? = null
    private var users: CollectionReference? = null

    private var usersNames: ArrayList<String>? = null
    private var layUsers: ArrayList<LinearLayout>? = null
    private var profiles: ArrayList<CircleImageView>? = null
    private var layoutMain: LinearLayout? = null

    private var searchName: EditText? = null

    //bottom buttons
    private var addButton: ImageButton? = null
    private var homeButton: ImageButton? = null
    private var aboutButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var profileButton: ImageButton? = null
    private var otherUserId: String? = null
    private var userId: String? = null
    private var docUser: DocumentReference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friends)


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()
        currentUserId = mAuth!!.currentUser!!.uid

        users = fStore!!.collection(FireStoreInfo.usersCollection)


        layoutMain = findViewById(R.id.layoutMain);
        usersNames = ArrayList()
        layUsers = ArrayList()
        profiles = ArrayList()
        searchName = findViewById(R.id.searchName)


        val extras = intent.extras
        if (extras != null) {
            userId = intent.getStringExtra("currUserId")

        }

        docUser = users!!.document(userId!!)








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

        //RECOMENDED USERS APPEAR WITHOUT SEARCH
        searchName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val txt = searchName!!.getText().toString()
                if (txt.length > 0) {
                    for (i in 0 until usersNames!!.size) {
                        if (!usersNames!!.get(i).startsWith(txt)) layUsers!![i].visibility =
                            View.GONE else layUsers!![i].visibility =
                            View.VISIBLE
                    }
                } else {
                    for (i in 0 until usersNames!!.size) {
                        layUsers!![i].visibility = View.VISIBLE
                    }
                }
            }
        })

        createLayout("Miguel","guitar, drums, bass")
    }



    private fun createLayout(userX: String, instruments: String) {
        var instrumentText = instruments
        val lin = LinearLayout(this)
        val layParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val dpPad20 = convertDpToPixel(20f, applicationContext).toInt()
        val dpPad10 = convertDpToPixel(10f, applicationContext).toInt()
        val dpPad5 = convertDpToPixel(5f, applicationContext).toInt()
        var dpPad2 = convertDpToPixel(2f, applicationContext).toInt()


        layParam.setMargins(dpPad10, dpPad20, dpPad10, dpPad10)
        lin.orientation = LinearLayout.HORIZONTAL
        lin.setPadding(0, 0, 0, dpPad10)
        lin.weightSum = 6f
        lin.setBackgroundResource(R.drawable.border_friends)
        lin.layoutParams = layParam
        val img = CircleImageView(this)
        img.borderColor = Color.BLACK
        img.borderWidth = dpPad2
        val dpPad55 = convertDpToPixel(55f, applicationContext).toInt()
        val layParam2 = LinearLayout.LayoutParams(0, dpPad55, 1f)
        layParam2.setMargins(0, dpPad2, dpPad10, dpPad2)
        layParam2.gravity = Gravity.CENTER
        img.setImageResource(R.drawable.default2)
        img.layoutParams = layParam2

        //layout VERTICAL

        val linLay = LinearLayout(this)
        val layParam3 = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 5f)
        linLay.orientation = LinearLayout.VERTICAL
        linLay.layoutParams = layParam3


        // lin HORIZONTAL
        val linNameAndSkill = LinearLayout(this)
        val layParams4 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            4f
        )
        val dp3 = convertDpToPixel(3f, this).toInt()
        layParams4.setMargins(0, dp3, 0, 0)
        linNameAndSkill.weightSum = 2f
        linNameAndSkill.orientation = LinearLayout.HORIZONTAL
        linNameAndSkill.layoutParams = layParams4


        // NAME TEXT VIEW
        val name = TextView(this)
        val layParam4 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        //  layParam4.gravity = Gravity.CENTER
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
        name.text = userX
        name.setTextColor(Color.BLACK)
        name.layoutParams = layParam4
        linNameAndSkill.addView(name);

        /*
        //SKILL OU ENT TROPHYS
        val numberMembers = TextView(this)
        numberMembers.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        numberMembers.text = userX
        numberMembers.setTextColor(Color.rgb(165,230,186))
        numberMembers.layoutParams = layParam4

        linNameAndSkill.addView(numberMembers)

         */

        //INSTRUMENTS TEXT VIEW
        val instrumentTextF = TextView(this)
        instrumentTextF.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        instrumentTextF.setTextColor(Color.rgb(91, 42, 134))
        if (instrumentText.length > 40) instrumentText = instrumentText.substring(0, 40)
        instrumentTextF.text = instrumentText
        instrumentTextF.layoutParams = layParam4
        // linLay.addView(name)
        linLay.addView(linNameAndSkill)
        linLay.addView(instrumentTextF)
        lin.addView(img)
        lin.addView(linLay)
        usersNames!!.add(userX)
        layUsers!!.add(lin)
        profiles!!.add(img)
        layoutMain!!.addView(lin)
    }


/*
    private fun getUserInfoDb(){
//docUser

    docUser!!.get().addOnCompleteListener(OnCompleteListener {
            task ->
        if (task.isSuccessful) {
            var docOtherUser: DocumentReference = users!!.document(otherUserId!!)
            val document = task.result
            if (document != null) {
            }
            if (document!!.get("friendResquests") != null) {
                docOtherUser = document.get("friendResquests") as ArrayList<String>

            }


        }
        })





    }

*/









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
        val c=0
        for (i in arr) {
            if (strFinal.equals("")){
                strFinal +=  i
            }
            else if (!strFinal.equals("")){
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