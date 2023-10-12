package com.abdiel.destinationcatalogue.data.destination


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    @SerializedName("address")
    val address: String?,
    @SerializedName("budget")
    val budget: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("facility")
    val facility: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photo")
    val photo: String?

) : Parcelable