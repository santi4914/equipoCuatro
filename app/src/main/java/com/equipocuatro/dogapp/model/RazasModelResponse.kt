package com.equipocuatro.dogapp.model

import com.google.gson.annotations.SerializedName

// Respuesta de breeds/list/all: cada llave de "message" es una raza.
data class RazasModelResponse(
    @SerializedName("message")
    val message: Map<String, List<String>>,

    @SerializedName("status")
    val status: String
)
