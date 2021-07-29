package com.webtechsolution.ghumantey.testUtil

object SignUpUtil {
    private val existingUser = listOf<String>("Neha","Sakar")

    fun validateSignUpInput(
        username:String,
        password:String,
        isAgency:Boolean
    ):Boolean{
        if(username.isEmpty() || password.isEmpty()) {
            return false
        }
        if(username in existingUser) {
            return false
        }
        return true
    }
}