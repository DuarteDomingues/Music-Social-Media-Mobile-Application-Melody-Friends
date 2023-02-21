package com.example.projeto_dam

class User {

    private var userName:String? = null
    private var email:String? = null



    constructor(){

    }

    constructor(username:String, email:String){

        this.userName = username
        this.email = email
    }

    public fun getUserName() : String{

        return this.userName!!
    }

    public fun setUserName(name: String){

        this.userName = name

    }

    public fun getEmail() : String{

        return this.email!!
    }

    public fun setEmail(name:String){
        this.email = name
    }




}