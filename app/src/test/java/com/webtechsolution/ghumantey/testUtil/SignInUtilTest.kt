package com.webtechsolution.ghumantey.testUtil

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class SignInUtilTest{

    @Test
    fun `empty username return false`(){
        val result = SignUpUtil.validateSignUpInput(
            "",
            "admin123",
            false
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun empty_password_return_false(){
        val result = SignUpUtil.validateSignUpInput(
            "Niyati",
            "",
            false
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun already_exist_username_return_false(){
        val result = SignUpUtil.validateSignUpInput(
            "Sakar",
            "admin123",
            false
        )

        Truth.assertThat(result).isFalse()
    }

    @Test
    fun correct_username_password_as_user_return_true(){
        val result = SignUpUtil.validateSignUpInput(
            "Niyati",
            "admin123",
            false
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun correct_username_password_as_agency_return_true(){
        val result = SignUpUtil.validateSignUpInput(
            "Yeti Travels",
            "admin123",
            true
        )
        Truth.assertThat(result).isTrue()
    }
}