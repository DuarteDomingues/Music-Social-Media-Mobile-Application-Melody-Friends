package com.example.projeto_dam

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class Publications : AppCompatActivity() {

    private var fStore: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserId: String? = null
    private var publications: CollectionReference? = null


    private var linConteudos: ArrayList<LinearLayout>? = null
    private var linIcons: ArrayList<LinearLayout>? = null
    private var txtLikes: ArrayList<TextView>? = null
    private var linContent: LinearLayout? = null
   // private lateinit var btnsLike: ArrayList<ImageButton>

     var btnsLike: MutableList<ImageButton> = ArrayList()
    var btnsVals: MutableList<TextView> = ArrayList()

    private var aboutButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var friendButton: ImageButton? = null
    private var addButton: ImageButton? = null
    private var profileButton: ImageButton? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts)

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()
        currentUserId = mAuth!!.currentUser!!.uid

        publications = fStore!!.collection(FireStoreInfo.publicationCollection)
        linConteudos = ArrayList()
        linIcons = ArrayList()
        txtLikes = ArrayList()
        linContent = findViewById(R.id.linContent);

        aboutButton = findViewById(R.id.logout)
        addButton = findViewById(R.id.add)
        profileButton = findViewById(R.id.profileButton)

        addButton!!.setOnClickListener{

            val intent = Intent(this, CreateContent::class.java)
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


        getPublicationInfoDb()




    }


    @SuppressLint("SetTextI18n")
    private fun createMainLinearLayout(
        titleName: String,
        username: String,
        locationD: String,
        index: String,
        story: String,
        numLikes: Long
    ){
        //layout principal
        val lin = LinearLayout(this)
        lin.setBackgroundResource(R.drawable.post_border)
        lin.orientation = LinearLayout.VERTICAL
        val layParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layParams.setMargins(
            0,
            convertDpToPixel(10f, applicationContext).toInt(),
            0,
            convertDpToPixel(
                10f,
                applicationContext
            ).toInt()
        )
        lin.layoutParams = layParams

        //mostrar titulo

        val lin7: LinearLayout = getGenericLinearLayout()

        //mostrar title
        val title = TextView(this)
        val layTitle = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val dpPad7 = convertDpToPixel(3f, applicationContext).toInt()
        title.setPadding(0, dpPad7, 0, dpPad7)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        title.setTextColor(Color.rgb(91, 42, 134))
        title.text = titleName
        title.gravity = 1
        title.layoutParams = layTitle
        lin7.addView(title)


        val lin2: LinearLayout = getGenericLinearLayout()

        //mostrar username
        var user = TextView(this)

        val layMatch = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,

        )
        val dpPad3 = convertDpToPixel(3f, applicationContext).toInt()
        val dp10 = convertDpToPixel(10f, applicationContext).toInt()
        user.setPadding(dp10, dpPad3, 0, dpPad3)
        user.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
        user.setTextColor(Color.WHITE)
        user.text = username
        user.layoutParams = layMatch
        lin2.addView(user)


        val lin8: LinearLayout = getGenericLinearLayout()

        val location = TextView(this)

        val layLoc = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,

            )
        location.setPadding(dp10, 0, 0, dpPad7)

        location.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        location.setTextColor(Color.rgb(91, 42, 134))
        location.text = locationD
        location.layoutParams = layLoc
        lin8.addView(location)


        //mostrar TEXTO
        val lin3: LinearLayout = getGenericLinearLayout()
        val textoPost = TextView(this)
        val dpPad5 = convertDpToPixel(5f, applicationContext).toInt()
        val dpPad10 = convertDpToPixel(10F, applicationContext).toInt()
        textoPost.setPadding(dpPad10, dpPad10, dpPad5, dpPad10)
        textoPost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        textoPost.text = story
        textoPost.layoutParams = layMatch
        textoPost.setTextColor(Color.WHITE)
        lin3.addView(textoPost)


        //mostrar likes e comentarios
        val lin4: LinearLayout = getGenericLinearLayout()
        val comments = TextView(this)
        val layWrap = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        var likes = TextView(this)
        val layComments = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        likes.setPadding(dpPad10, dpPad10, 0, dpPad5)
        likes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        likes.text = "" + numLikes
        likes.setTextColor(Color.WHITE)
        layComments.gravity = Gravity.END
        likes.layoutParams = layComments
        txtLikes!!.add(likes)
        lin4.addView(likes)
        lin4.addView(comments)


        //botao de like e comment
        val lin5 = LinearLayout(this)
        val layparam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lin5.orientation = LinearLayout.HORIZONTAL
        lin5.layoutParams = layparam
        var likeBtn = ImageButton(this)
        likeBtn.tag = username + index
        likeBtn.setBackgroundColor(Color.TRANSPARENT)
        likeBtn.scaleType = ImageView.ScaleType.FIT_CENTER
        likeBtn.setBackgroundResource(R.drawable.likezao)

        val lay = LinearLayout(this)
        val layAround = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        lay.layoutParams = layAround
        val dpPad38 = convertDpToPixel(38F, applicationContext).toInt()
        val dpPad32 = convertDpToPixel(32F, applicationContext).toInt()
        val dpPad34 = convertDpToPixel(34F, applicationContext).toInt()
        val layImg = LinearLayout.LayoutParams(dpPad38, dpPad32)
        layImg.setMargins(dpPad3, dpPad5, 0, 0)



        likeBtn.layoutParams = layImg
        lay.addView(likeBtn)
        lay.setPadding(dpPad10, dpPad5, dpPad5, dpPad5)

        val layImg2 = LinearLayout.LayoutParams(dpPad34, dpPad32)
        layImg2.setMargins(0, 0, dpPad3, 0)
        lin5.addView(lay)
        linIcons!!.add(lin5)
        lin.addView(lin7)
        lin.addView(lin2)


        lin.addView(lin3)
        lin.addView(lin4)

        lin.addView(lin5)
        lin.addView(lin8)
        linConteudos!!.add(lin)
        linContent!!.addView(lin)
        btnsLike.add(likeBtn)
        btnsVals.add(likes)
    }


    private fun getGenericLinearLayout(): LinearLayout {
        val layParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        //layout do texto
        val lin = LinearLayout(this)
        lin.setBackgroundResource(R.drawable.text_lines);
        lin.orientation = LinearLayout.HORIZONTAL
        lin.layoutParams = layParams
        return lin
    }


    private fun addEmptySpace() {
        val dpPad56 = convertDpToPixel(56f, applicationContext).toInt()
        val commentBtn = ImageButton(this)
        commentBtn.setBackgroundColor(Color.TRANSPARENT)
        val layImg2 = LinearLayout.LayoutParams(dpPad56, dpPad56)
        commentBtn.layoutParams = layImg2
        linContent!!.addView(commentBtn)
    }


    private fun getPublicationInfoDb() {

        publications!!.orderBy("timeMilis",
            Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->

            if (documents != null) {

                var docLen = documents.size()
                var c=0
                var arrCommented = ArrayList<Int>()
                var arrIds = ArrayList<Long>()
                var namesArr = ArrayList<ArrayList<String>>()

                for (document in documents) {
                    c++
                    Log.d(TAG, "${document.id} => ${document.data}")

                    var userNameDoc = document.getString("userName")
                    var titleDoc = document.getString("title")
                    var textDoc = document.getString("text")
                    var dateDoc = document.getString("pubDate")
                    var location = document.getString("location")
                    var likesArr = document.get("likes") as ArrayList<String>
                    var pubId = document.getLong("timeMilis")
                    arrIds.add(pubId!!)
                    var likeVal = likesArr.size.toLong()

                    var dateLoc = location + "  "+dateDoc

                    createMainLinearLayout(titleDoc!!,userNameDoc!!,dateLoc,"0",textDoc!!,likeVal!!);

                    var checkCommented = checkUserCommented(likesArr)
                    if (checkCommented){
                        arrCommented.add(c-1)
                    }
                    namesArr.add(likesArr)


                    if (c == docLen){
                        likeButtonHandler(arrCommented!!, arrIds!!, namesArr)
                    }

                }
                addEmptySpace();

            }
        }
    }

    private fun likeButtonHandler(arr: ArrayList<Int>,arrIds: ArrayList<Long>, arrlikeName: ArrayList<ArrayList<String>> ){

        //btnsVals
        for (i in 0..btnsLike.size-1){

            if (!arr.contains(i)) {
                btnsLike[i].setOnClickListener {

                    btnsLike[i]!!.setAlpha(0f);

                    //Animate the alpha value to 1f and set duration as 1.5 secs.
                    btnsLike[i]!!.animate().alpha(1f).setDuration(700);


                    updateLikeText(btnsVals[i])
                    btnsLike[i].setBackgroundResource(R.drawable.like2)
                    btnsLike[i].setOnClickListener(null)

                    arrlikeName[i].add(currentUserId!!)
                    val map = hashMapOf<String, Any>()
                    map.put("likes", arrlikeName[i])

                    var documentReference: DocumentReference =
                        fStore!!.collection(FireStoreInfo.publicationCollection).document(arrIds[i].toString())
                    documentReference.set(map, SetOptions.merge())

                }
            }
            else{
                btnsLike[i].setBackgroundResource(R.drawable.like2)
            }

        }

    }




    private fun checkUserCommented(arr: ArrayList<String>): Boolean{

        for ( i in arr){

            if (currentUserId.equals(i)){
                return true
            }

        }
        return false
    }


    private fun updateLikeText(tView: TextView){

        var textInt = tView.text.toString().trim().toInt()
        textInt ++
        tView.setText(textInt.toString())



    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.getResources()
            .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }


}