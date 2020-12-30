package com.webtechsolution.ghumantey.data

import com.webtechsolution.ghumantey.data.model.Register
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("firstname") firstName:String,
        @Field("lastname") lastName:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):Single<Register>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ):Single<Register>

}