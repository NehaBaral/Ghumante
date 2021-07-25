package com.webtechsolution.ghumantey.data

import com.webtechsolution.ghumantey.data.domain.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

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
    fun getPackagesList(): Single<AgencyPackage>

    @POST("search")
    fun getSearchPackages(@Body destination: SearchBody): Single<SearchPackage>

    @GET("packages/{id}")
    fun getPackageDetail(@Path("id") packageId: String): Single<PackageDetail>

    @POST("packages/{id}/comments")
    fun postComment(
        @Header("Authorization") token: String,
        @Path("id") packageId: String,
        @Body commentBody: CommentBody
    ): Single<PackagesListItem>

    @GET("packages/{id}/comments")
    fun getCommentList(@Path("id") packageId: String): Single<CommentList>

    /*@GET("destination/{id}/packages")
    fun getDestinationPackages(@Path("id") destinationId:String):Single<PackagesModel>*/

    @POST("users/signup")
    fun userRegister(@Body signUpBody: SignUpBody): Single<SignUp>

    @POST("users/login")
    fun userLogin(@Body signUpBody: SignUpBody): Single<Login>

    @GET("agencyPackage")
    fun getAgencyByPackage(@Header("Authorization") token: String): Single<AgencyPackage>

    @POST("packages")
    fun obtainPackagesList(
        @Header("Authorization") token: String,
        @Body postPackage: PostPackage
    ): Single<PackagesListItem>

    @GET("myBookings")
    fun getBookingPackage(@Header("Authorization") token: String): Single<List<SearchPackageItem>>

    @POST("packages/{id}/bookings")
    fun bookPackage(
        @Header("Authorization") token: String,
        @Path("id") packageId: String,
        @Body packages: BookPackageBody
    ): Single<PackagesListItem>

    @Multipart
    @POST("/saveImage")
    fun uploadImage(@Part parents:List<MultipartBody.Part>):Single<FileName>
}