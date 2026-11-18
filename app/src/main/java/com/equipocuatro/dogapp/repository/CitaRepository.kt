package com.equipocuatro.dogapp.repository

import android.content.Context
import com.equipocuatro.dogapp.data.CitaDB
import com.equipocuatro.dogapp.data.CitaDao
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.webservice.ApiService
import com.equipocuatro.dogapp.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitaRepository(val context: Context) {
    private val citaDao: CitaDao = CitaDB.getDatabase(context).citaDao()
    private val apiService: ApiService = ApiUtils.getApiService()

    suspend fun saveCita(cita: Cita, messageResponse: (String) -> Unit) {
        try {
            withContext(Dispatchers.IO) {
                citaDao.saveCita(cita)
            }
            messageResponse("Cita guardada correctamente")
        } catch (e: Exception) {
            messageResponse("Error al guardar la cita: ${e.message}")
        }
    }

    suspend fun getListCitas(): MutableList<Cita> {
        return withContext(Dispatchers.IO) {
            citaDao.getListCitas()
        }
    }

    suspend fun deleteCita(cita: Cita) {
        withContext(Dispatchers.IO) {
            citaDao.deleteCita(cita)
        }
    }

    suspend fun updateCita(cita: Cita) {
        withContext(Dispatchers.IO) {
            citaDao.updateCita(cita)
        }
    }

    suspend fun getRazas(): MutableList<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getRazas()
                response.message.keys.toMutableList()
            } catch (e: Exception) {
                e.printStackTrace()
                mutableListOf()
            }
        }
    }

    suspend fun getImagenRaza(raza: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getImagenRaza(raza)
                response.message
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }
}
