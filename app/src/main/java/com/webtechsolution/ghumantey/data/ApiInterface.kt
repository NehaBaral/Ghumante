package com.webtechsolution.ghumantey.data

import com.webtechsolution.ghumantey.data.model.Auth
import com.webtechsolution.ghumantey.data.model.DestinationList
import com.webtechsolution.ghumantey.data.model.Register
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.StringReader

interface ApiInterface {

    @FormUrlEncoded
    @POST("signup")
    fun userRegister(
        @Field("username") username:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):Single<Register>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ):Single<Auth>

    @GET("/destinations")
    fun getUserDestination():Single<DestinationList>

}