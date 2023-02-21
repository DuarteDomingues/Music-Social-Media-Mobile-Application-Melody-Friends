package com.example.projeto_dam

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


//import com.google.firebase.storage.StorageReference


class EditUserProfile : AppCompatActivity() {

    private var locationField: EditText? = null
    private var favField: EditText? = null
    private var bioField: EditText? = null
    private var editButton: Button? = null
    private var fStore: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserId: String? = null
    private var locationIntent: String? = null
    private var bioIntent: String? = null
    private var favArtistsIntent: String? = null
    private var addButton: ImageButton? = null
    private var editImage: ImageView? = null
    private var imageURI: Uri? = null
    private var imageURIString: String? = null

    private var imagesRef: StorageReference? = null
    private var  uriImg: Uri? = null
    //user collection
    private var docUser: DocumentReference? = null
    private var changedImg : Boolean = false

    //buttons de baixo
    private var homeButton: ImageButton? = null
    private var aboutButton: ImageButton? = null
    private var searchButton: ImageButton? = null
    private var friendButton: ImageButton? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_profile)

        fStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance();


        currentUserId = mAuth!!.currentUser!!.uid

        addButton = findViewById(R.id.add)
        editImage = findViewById(R.id.editprofileImg)


        homeButton = findViewById(R.id.home)
        aboutButton = findViewById(R.id.logout)

        homeButton!!.setOnClickListener{

            val intent = Intent(this, Publications::class.java)
            startActivity(intent)

        }

        aboutButton!!.setOnClickListener{

            val intent = Intent(this, About::class.java)
            startActivity(intent)

        }





        val users: CollectionReference? = fStore!!.collection(FireStoreInfo.usersCollection)
        docUser = users!!.document(currentUserId!!)


        val extras = intent.extras
        if (extras != null) {
            Log.d("recebiarray", "recebiarray")
            locationIntent = intent.getStringExtra("locIntent")
            bioIntent = intent.getStringExtra("bioIntent")
            favArtistsIntent = intent.getStringExtra("favArtistsIntent")



        }

        val storageRef = FirebaseStorage.getInstance().reference

        //colecao da storage
        imagesRef = storageRef.child("profile")



        locationField = findViewById(R.id.editLocation)
        favField = findViewById(R.id.editFavoriteArtists)
        bioField = findViewById(R.id.bio)
        editButton = findViewById(R.id.btn_submit_edit)

        locationField!!.hint  = locationIntent
        favField!!.hint = favArtistsIntent
        bioField!!.hint = bioIntent

        setProfileImg()

        handleEditButton()
        handleAddButton()
        handleEditImage()

    }


    private fun setProfileImg(){

        docUser!!.get().addOnCompleteListener(OnCompleteListener {
                task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    if (document.getString("userImage")!=null) {

                        var userImg = document.getString("userImage")!!


                        Picasso.with(this).load(userImg).fit().centerInside().into(editImage)
                    }
                }
            }

        })

        // var str_img = "https://firebasestorage.googleapis.com/v0/b/melody-friends.appspot.com/o/profile%2Fimages385303785?alt=media&token=fa5edce4-e5a3-457e-b25d-198be3d4bd73"
       // Picasso.with(this).load(str_img).fit().centerInside().into(editImage);

    }


    private fun handleEditButton() {

        var location = ""
        var favArtists = ""
        var bio = ""

        editButton!!.setOnClickListener {

            //Log.w("imageConaInicial",initialImg!!)

            if (changedImg) {
                Log.w("imagem foi alterada", "imagem foi alterada")

                writeImageInStorage()
            }
            else{
                updateUser()
            }


        }

    }

    private fun updateUser(){
        var location = ""
        var favArtists = ""
        var bio = ""

            location = locationField!!.text.toString()
            favArtists = favField!!.text.toString()
            bio = bioField!!.text.toString()

            val map = hashMapOf<String, Any>()
            //TODO
            if (changedImg) {
                map.put("userImage", uriImg!!.toString())
            }
        if (location != "" && location != locationIntent) {
                map.put("location", location!!)
            }
            if (favArtists != "" && favArtists != favArtistsIntent ) {
                map.put("favoriteArtists", favArtists!!)
            }
            if (bio!= "" && favArtists != bioIntent ) {
                map.put("bio", bio!!)
            }

            var documentReference: DocumentReference =
                fStore!!.collection(FireStoreInfo.usersCollection).document(currentUserId!!)
            documentReference.set(map, SetOptions.merge()).addOnSuccessListener(this) {

            var intent = Intent(this, UserProfile::class.java)
            startActivity(intent)

            }
        var intent = Intent(this, UserProfile::class.java)
        startActivity(intent)

    }



    private fun handleAddButton(){

        addButton!!.setOnClickListener{

            val intent = Intent(this, CreateContent::class.java)
            startActivity(intent)

        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1003)



    }


    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1003) {

            Log.w("CHEGUEI","CHEGUEI")

            imageURI = data!!.data
           // val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageURI)
            //editImage!!.setImageBitmap(bitmap)

            assert(imageURI != null)
            Picasso.with(this).load(imageURI).fit().centerInside().into(editImage)
            Log.w("imageURI",imageURI.toString())

            changedImg=true

        }
    }

    private fun writeImageInStorage(){

        var imgStorage : StorageReference = imagesRef!!.child("images"+imageURI!!.lastPathSegment)

        //escrever na colecao profile, a imagem assim images145061094
        imgStorage.putFile(imageURI!!).addOnFailureListener {
            Toast.makeText(
                this,
                "Request failed",
                Toast.LENGTH_LONG
            ).show()
        }
            .addOnSuccessListener {
                imgStorage.downloadUrl.addOnSuccessListener { uri ->

                    uriImg =uri
                    Log.w("uri",uriImg.toString())

                    //chama metodo para escrever no userImage
                    updateUser()


                }
            }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickImageFromGallery() else Toast.makeText(
                this,
                "Request denied..",
                Toast.LENGTH_LONG
            ).show()
        }
    }



    private fun handleEditImage(){

        editImage!!.setOnClickListener{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //sem permissão, vai pedir
                    val permissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, 1001)
                } else {
                    //tem permissão
                        Log.w("tenhopermissao","permissao")
                    pickImageFromGallery()
                }
            } else {
                //sistema menos do marsmello
                Log.w("tenhopermissao2","permissao")

                 pickImageFromGallery()
            }

        }

    }


}









