package com.developance.imaginary.photos.domain

import com.google.gson.annotations.SerializedName


data class Photographer(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_image") val profileImage: ProfileImage? = ProfileImage(),
    @SerializedName("instagram_username") val instagramUsername: String? = null,
    @SerializedName("username") val userName : String ,
    @SerializedName("total_collections") val totalCollections: Int? = null,
    )

data class ProfileImage(

    @SerializedName("small") var small: String? = null,
    @SerializedName("medium") var medium: String? = null,
    @SerializedName("large") var large: String? = null

)