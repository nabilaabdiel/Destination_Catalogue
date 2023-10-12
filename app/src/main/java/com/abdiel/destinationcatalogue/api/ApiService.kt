package com.abdiel.destinationcatalogue.api

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    //Login
    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun login(
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ): String

    //Register
    @FormUrlEncoded
    @POST("api/auth/register")
    suspend fun register(
        @Field("username") username: String?,
        @Field("email") email: String?,
        @Field("phone") phone: String?,
        @Field("password") password: String?,
        @Field("confirm_password") confirm_password: String?
    ): String



    //Change Password
    @FormUrlEncoded
    @POST("auth/change-password")
    suspend fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
        @Field("password_confirmation") password_confirmation: String
    ): String

    //Get Notes
    @GET("notes")
    suspend fun getNotes(): String

    //Get Favorite
    @GET("notes/favorite")
    suspend fun getFav(): String

    //Get Notes By Categories
    @GET("categories/:id/notes")
    suspend fun getNoteByCategories(): String

    //Get Notes By Id
    @GET("notes/{id}")
    suspend fun getNoteById(): String

    //Favorite Notes
    @POST("notes/{id_note}/favorite")
    suspend fun favNotes(
        @Path("id_note") id_note: String?,
        @Body favorite: RequestBody
    ): String

    //Create Notes
    @FormUrlEncoded
    @POST("notes")
    suspend fun createNotes(
        @Field("title") title: String?,
        @Field("body") body: String?
//        @Field("category_id") category_id: String?
    ): String

    /////////////////////////////////////////////////////////////////////////////

    //User

    //Renew Token
    @POST("api/auth/renew_token")
    suspend fun renewToken(
    ): String

    //Get Profile
    @GET("api/auth/getprofile")
    suspend fun getProfile(): String

    //Logout
    @DELETE("api/auth/logout")
    suspend fun logout(): String

    //Update Profile
    @FormUrlEncoded
    @POST("api/auth/editprofile")
    suspend fun editProfile(
        @Field("username") username: String?
    ): String

    //Update Profile
    @Multipart
    @POST("api/auth/editprofile")
    suspend fun editProfileImg(
        @Part("username") username: String?,
        @Part photo: MultipartBody.Part?
    ): String

    //Destin

    //Image Slider
    @GET("api/auth/img_slider?limit=5")
    suspend fun imageSlider(
    ) : String

    //Get All List
    @GET("api/auth/all_destinations")
    suspend fun allDestinations(
    ) : String

    //Get Children List
    @GET("api/auth/children_destinations")
    suspend fun childrenDestinations(
    ) : String

    //Get Nature List
    @GET("api/auth/nature_destinations")
    suspend fun natureDestinations(
    ) : String
}