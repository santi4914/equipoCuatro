package com.equipocuatro.dogapp.model

import com.google.gson.annotations.SerializedName

data class ImagenRazaModelResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String
)
