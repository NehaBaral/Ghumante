package com.webtechsolution.ghumantey.data

import com.webtechsolution.ghumantey.data.model.Auth
import com.webtechsolution.ghumantey.data.model.DestinationList
import com.webtechsolution.ghumantey.data.model.PackagesModel
import com.webtechsolution.ghumantey.data.model.Register
import io.reactivex.Single
import retrofit2.http.*
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

    @GET("destinations")
    fun getUserDestination():Single<DestinationList>

    @GET("destination/{id}/packages")
    fun getDestinationPackages(@Path("id") destinationId:String):Single<PackagesModel>

}