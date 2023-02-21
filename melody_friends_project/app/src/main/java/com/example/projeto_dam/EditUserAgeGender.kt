package com.example.projeto_dam

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*
import kotlin.collections.ArrayList


class EditUserAgeGender: AppCompatActivity() {


    private var datePickerDialog: DatePickerDialog? = null
    private var dateButton: Button? = null
    private var spinnerGender: Spinner? = null
    private var spinnerSkill: Spinner? = null
    private var genresArr: ArrayList<String>? = null
    private var instrumentsArr: ArrayList<String>? = null
    private var nextButton: ImageButton? = null
    private var age: Int? = null
    private var gender: Boolean? = null
    private var skill: String? = null
    private var fStore: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserId: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_edit)

        nextButton = findViewById(R.id.nextGoToProfile)
        fStore =  FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance();

        currentUserId = mAuth!!.currentUser!!.uid

        genresArr = ArrayList<String>()
        instrumentsArr = ArrayList<String>()



        val extras = intent.extras
        if (extras != null){
            Log.d("recebiarray","recebiarray")
            instrumentsArr = intent.getStringArrayListExtra("arrIns")
            genresArr = intent.getStringArrayListExtra("arrGenres")
        }



        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton!!.setText(getTodaysDate());
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerSkill = findViewById(R.id.spinnerSkillFirst)
        setUpSpinners()

        nextButton!!.setOnClickListener{
            nextButton()
        }

    }
    private  fun getDate(){

        var dateRaw = dateButton!!.text as String
        var month = dateRaw.substring(0, 3).trim()
        var day = dateRaw.substring(4,6)
        var year = ""

        var dayInt = day.trim().toInt()

        if (dayInt <10){

            year = dateRaw.substring(6,10)
        }
        else{
            year = dateRaw.substring(7,11)
        }

        var yearInt = year.trim().toInt()
        var intMonth = monthToInt(month)

        age = getAge(yearInt,intMonth!!,dayInt)

    }

    private fun monthToInt(month:String?):Int?{
        var intMonth = 0
        when(month) {

            "Jan" -> intMonth = 0
            "Feb" -> intMonth = 1
            "Mar" -> intMonth = 2
            "Apr" -> intMonth = 3
            "May" -> intMonth = 4
            "Jun" -> intMonth = 5
            "Jul" -> intMonth = 6
            "Aug" -> intMonth = 7
            "Sep" -> intMonth = 8
            "Oct" -> intMonth = 9
            "Nov" -> intMonth = 10
            "Dec" -> intMonth = 11


        }
        return intMonth
    }


    private fun getGender(){

        var genderStr = spinnerGender!!.selectedItem.toString().trim()

        if (genderStr.equals("Female")){

            gender = true

        }

        else{
            gender = false
        }
    }

    private fun getSkil(){

        skill = spinnerSkill!!.selectedItem.toString()
    }

    fun getAge(DOByear: Int, DOBmonth: Int, DOBday: Int): Int {
        var age: Int
        val calenderToday = Calendar.getInstance()
        val currentYear = calenderToday[Calendar.YEAR]
        val currentMonth = 1 + calenderToday[Calendar.MONTH]
        val todayDay = calenderToday[Calendar.DAY_OF_MONTH]
        age = currentYear - DOByear
        if (DOBmonth > currentMonth) {
            --age
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age
            }
        }
        return age
    }




    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date = makeDateString(day, month, year)
                dateButton!!.text = date
            }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private fun getTodaysDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month = month + 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return getMonthFormat(month).toString() + " " + day + " " + year
    }
    private fun getMonthFormat(month: Int): String? {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"

        //default should never happen
    }

    fun openDatePicker(view: View?) {
        datePickerDialog!!.show()
    }


    private fun setUpSpinners(){

        val myAdapter = ArrayAdapter(
            this,
            R.layout.edit_spinner, resources.getStringArray(R.array.genders)
        )

        val skillAdapter = ArrayAdapter(
            this,
            R.layout.edit_spinner, resources.getStringArray(R.array.skill)
        )


        myAdapter.setDropDownViewResource(R.layout.edit_spinner)
        spinnerGender!!.setAdapter(myAdapter)

        myAdapter.setDropDownViewResource(R.layout.edit_spinner)
        spinnerSkill!!.setAdapter(skillAdapter)



    }


    private fun nextButton(){

        getDate()
        getGender()
        getSkil()

        var imgDefault = "https://firebasestorage.googleapis.com/v0/b/melody-friends.appspot.com/o/profile%2Funcle_frank.jpg?alt=media&token=cae7f1c8-e6ef-4a1e-83df-ca2ad28b20b6"

        val map = hashMapOf<String, Any>()
        map.put("age", age!!)
        map.put("gender", gender!!)
        map.put("skill", skill!!)
        map.put("instruments",instrumentsArr!!)
        map.put("genres",genresArr!!)
        map.put("userImage", imgDefault!!)

        var  documentReference: DocumentReference = fStore!!.collection(FireStoreInfo.usersCollection).document(currentUserId!!)
        documentReference.set(map, SetOptions.merge()).addOnSuccessListener (this){

            var intent = Intent(this, UserProfile::class.java)
            startActivity(intent)



        }


    }


}