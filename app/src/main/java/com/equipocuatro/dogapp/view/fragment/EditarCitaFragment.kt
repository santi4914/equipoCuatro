package com.equipocuatro.dogapp.view.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.FragmentEditarCitaBinding
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.utils.Constants
import com.equipocuatro.dogapp.viewmodel.CitaViewModel

class EditarCitaFragment : Fragment() {
    private lateinit var binding: FragmentEditarCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()
    private lateinit var cita: Cita

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditarCitaBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        dataCita()
        observadorViewModel()
    }

    private fun dataCita() {
        cita = arguments?.getSerializable(Constants.CLAVE_CITA) as Cita
        binding.etNombreMascota.setText(cita.nombreMascota)
        binding.atRaza.setText(cita.raza)
        binding.etNombrePropietario.setText(cita.nombrePropietario)
        binding.etTelefono.setText(cita.telefono)
    }

    private fun controladores() {
        validarDatos()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnEditarCita.setOnClickListener {
            editarCita()
        }
    }

    private fun editarCita() {
        val raza = binding.atRaza.text.toString()
        if (raza == cita.raza) {
            updateCita(cita.urlImagen)
        } else {
            citaViewModel.getImagenRaza(raza.lowercase()) { urlImagen ->
                if (urlImagen.isEmpty()) {
                    Toast.makeText(context, getString(R.string.errorImagenRaza), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    updateCita(urlImagen)
                }
            }
        }
    }

    private fun updateCita(urlImagen: String) {
        val citaEditada = Cita(
            id = cita.id,
            nombreMascota = binding.etNombreMascota.text.toString(),
            raza = binding.atRaza.text.toString(),
            nombrePropietario = binding.etNombrePropietario.text.toString(),
            telefono = binding.etTelefono.text.toString(),
            sintoma = cita.sintoma,
            urlImagen = urlImagen
        )
        citaViewModel.updateCita(citaEditada) { actualizada ->
            if (actualizada) {
                Toast.makeText(context, getString(R.string.citaActualizada), Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_editarCitaFragment_to_homeFragment)
            } else {
                Toast.makeText(context, getString(R.string.errorActualizacion), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validarDatos() {
        val listEditText = listOf<EditText>(
            binding.etNombreMascota,
            binding.atRaza,
            binding.etNombrePropietario,
            binding.etTelefono
        )

        for (editText in listEditText) {
            editText.addTextChangedListener {
                val isListFull = listEditText.all {
                    it.text.isNotEmpty()
                }
                binding.btnEditarCita.isEnabled = isListFull
                if (isListFull) {
                    binding.btnEditarCita.setTypeface(null, Typeface.BOLD)
                } else {
                    binding.btnEditarCita.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    private fun observadorViewModel() {
        observerListRazas()
        observerProgress()
    }

    private fun observerProgress() {
        citaViewModel.progressState.observe(viewLifecycleOwner) { status ->
            binding.progress.isVisible = status
            binding.svFormulario.isVisible = !status
        }
    }

    private fun observerListRazas() {
        citaViewModel.getRazas()
        citaViewModel.listRazas.observe(viewLifecycleOwner) { listRazas ->
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                binding.root.context,
                android.R.layout.simple_dropdown_item_1line,
                listRazas
            )
            binding.atRaza.setAdapter(adapter)
        }
    }
}
