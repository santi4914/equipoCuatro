package com.equipocuatro.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.repository.CitaRepository
import kotlinx.coroutines.launch

class CitaViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val citaRepository = CitaRepository(context)

    private val _listCitas = MutableLiveData<MutableList<Cita>>()
    val listCitas: LiveData<MutableList<Cita>> get() = _listCitas

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    private val _listRazas = MutableLiveData<MutableList<String>>()
    val listRazas: LiveData<MutableList<String>> = _listRazas

    fun saveCita(cita: Cita, message: (String) -> Unit) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                citaRepository.saveCita(cita) { msg ->
                    message(msg)
                }
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun getListCitas() {
        viewModelScope.launch {
            _progressState.value = true
            try {
                _listCitas.value = citaRepository.getListCitas()
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun deleteCita(cita: Cita, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                citaRepository.deleteCita(cita)
                _progressState.value = false
                onComplete(true)
            } catch (e: Exception) {
                _progressState.value = false
                onComplete(false)
            }
        }
    }

    fun getRazas() {
        viewModelScope.launch {
            _progressState.value = true
            try {
                _listRazas.value = citaRepository.getRazas()
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun getImagenRaza(raza: String, urlImagen: (String) -> Unit) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                val url = citaRepository.getImagenRaza(raza)
                _progressState.value = false
                urlImagen(url)
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun updateCita(cita: Cita, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                citaRepository.updateCita(cita)
                _progressState.value = false
                onComplete(true)
            } catch (e: Exception) {
                _progressState.value = false
                onComplete(false)
            }
        }
    }
}
