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

    fun deleteCita(cita: Cita) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                citaRepository.deleteCita(cita)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun updateCita(cita: Cita) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                citaRepository.updateCita(cita)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }
}
