package com.webtechsolution.ghumantey.data

import com.webtechsolution.ghumantey.data.domain.*
import com.webtechsolution.ghumantey.data.model.Auth
import com.webtechsolution.ghumantey.data.model.DestinationList
import com.webtechsolution.ghumantey.data.model.PackagesModel
import com.webtechsolution.ghumantey.data.model.Register
import io.reactivex.Single
import retrofit2.http.*
import java.io.StringReader

interface ApiInterface {
    /*@FormUrlEncoded
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
    ):Single<Auth>*/

    @GET("Packages")
    fun getPackagesList():Single<PackagesList>

    @GET("search")
    fun getSearchPackages(
        @Body destination:SearchBody
    ):Single<SearchPackage>

    @GET("packages/{id}")
    fun getPackageDetail(@Path("id") packageId:String):Single<PackagesListItem>

    @POST("packages/{id}/comments")
    fun postComment(@Path("id") packageId:String,@Body commentBody:CommentBody):Single<PackagesListItem>

    @GET("packages/{id}/comments")
    fun getCommentList(@Path("id") packageId:String):Single<PackagesListItem>

    /*@GET("destination/{id}/packages")
    fun getDestinationPackages(@Path("id") destinationId:String):Single<PackagesModel>*/

    @POST("users/signup")
    fun userRegister(@Body signUpBody:SignUpBody):Single<SignUp>
}