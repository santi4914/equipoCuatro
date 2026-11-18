package com.equipocuatro.dogapp.model

import com.google.gson.annotations.SerializedName

data class RazasModelResponse(
    @SerializedName("message")
    val message: Map<String, List<String>>,

    @SerializedName("status")
    val status: String
)
