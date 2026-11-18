package com.equipocuatro.dogapp.model

import com.google.gson.annotations.SerializedName

// Respuesta de breed/{raza}/images/random: "message" es la URL de la imagen.
data class ImagenRazaModelResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String
)
