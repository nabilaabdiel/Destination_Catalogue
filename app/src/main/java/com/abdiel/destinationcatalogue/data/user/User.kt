package com.abdiel.destinationcatalogue.data.user


import com.google.gson.annotations.SerializedName
import java.io.File

data class User(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("profile_photo_path")
    val profile_photo_path: String?,
)