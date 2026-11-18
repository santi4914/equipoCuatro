package com.equipocuatro.dogapp.utils

object Constants {
    const val NAME_BD: String = "dogapp.db"

    // Clave del Bundle con la cita seleccionada.
    const val CLAVE_CITA: String = "cita"

    // API de razas de perros (dog.ceo).
    const val BASE_URL = "https://dog.ceo/api/"
    const val END_POINT_RAZAS = "breeds/list/all"
    const val END_POINT_IMAGEN_RAZA = "breed/{raza}/images/random"
}
