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
import com.equipocuatro.dogapp.databinding.FragmentNuevaCitaBinding
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.viewmodel.CitaViewModel

class NuevaCitaFragment : Fragment() {
    private lateinit var binding: FragmentNuevaCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNuevaCitaBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
    }

    private fun controladores() {
        validarDatos()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnGuardarCita.setOnClickListener {
            if (binding.spSintomas.selectedItemPosition == 0) {
                Toast.makeText(context, getString(R.string.seleccionaSintoma), Toast.LENGTH_SHORT)
                    .show()
            } else {
                saveCita()
            }
        }
    }

    private fun saveCita() {
        val nombreMascota = binding.etNombreMascota.text.toString()
        val raza = binding.atRaza.text.toString()
        val nombrePropietario = binding.etNombrePropietario.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val sintoma = binding.spSintomas.selectedItem.toString()

        citaViewModel.getImagenRaza(raza.lowercase()) { urlImagen ->
            if (urlImagen.isEmpty()) {
                Toast.makeText(context, getString(R.string.errorImagenRaza), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val cita = Cita(
                    nombreMascota = nombreMascota,
                    raza = raza,
                    nombrePropietario = nombrePropietario,
                    telefono = telefono,
                    sintoma = sintoma,
                    urlImagen = urlImagen
                )
                citaViewModel.saveCita(cita) { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
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
                binding.btnGuardarCita.isEnabled = isListFull
                if (isListFull) {
                    binding.btnGuardarCita.setTypeface(null, Typeface.BOLD)
                } else {
                    binding.btnGuardarCita.setTypeface(null, Typeface.NORMAL)
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
