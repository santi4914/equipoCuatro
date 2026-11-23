package com.equipocuatro.dogapp.webservice

import com.equipocuatro.dogapp.model.ImagenRazaModelResponse
import com.equipocuatro.dogapp.model.RazasModelResponse
import com.equipocuatro.dogapp.utils.Constants.END_POINT_IMAGEN_RAZA
import com.equipocuatro.dogapp.utils.Constants.END_POINT_RAZAS
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(END_POINT_RAZAS)
    suspend fun getRazas(): RazasModelResponse

    @GET(END_POINT_IMAGEN_RAZA)
    suspend fun getImagenRaza(@Path("raza") raza: String): ImagenRazaModelResponse
}
