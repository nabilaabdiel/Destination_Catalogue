package com.abdiel.destinationcatalogue.data.user


import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("photo")
    val photo: String?
)