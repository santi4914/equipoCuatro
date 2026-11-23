
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
import java.util.Locale

class EditarCitaFragment : Fragment() {

    private var _binding: FragmentEditarCitaBinding? = null
    private val binding get() = _binding!!

    private val citaViewModel: CitaViewModel by viewModels()

    private lateinit var cita: Cita
    private var guardando = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarCitaBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        // Recibir la cita seleccionada desde HU4
        @Suppress("DEPRECATION")
        cita = requireArguments()
            .getSerializable(Constants.CLAVE_CITA) as Cita

        cargarDatos()
        configurarVolver()
        validarCampos()
        configurarRazas()

        binding.btnEditarCita.setOnClickListener {
            guardarCambios()
        }
    }

    // Mostrar la información original de la cita
    private fun cargarDatos() {
        binding.etNombreMascota.setText(cita.nombreMascota)
        binding.atRaza.setText(cita.raza, false)
        binding.etNombrePropietario.setText(cita.nombrePropietario)
        binding.etTelefono.setText(cita.telefono)
    }

    // Regresar al detalle de la cita
    private fun configurarVolver() {
        binding.toolbar.setNavigationOnClickListener {
            if (!guardando) {
                findNavController().popBackStack()
            }
        }
    }

    // Validar los cuatro campos obligatorios
    private fun validarCampos() {
        val campos = listOf<EditText>(
            binding.etNombreMascota,
            binding.atRaza,
            binding.etNombrePropietario,
            binding.etTelefono
        )

        val actualizarEstado = {
            val completos = campos.all {
                it.text.toString().isNotBlank()
            }

            binding.btnEditarCita.isEnabled =
                completos && !guardando

            binding.btnEditarCita.setTypeface(
                null,
                if (completos) Typeface.BOLD
                else Typeface.NORMAL
            )
        }

        campos.forEach { campo ->
            campo.addTextChangedListener {
                actualizarEstado()
            }
        }

        actualizarEstado()
    }

    // Consultar las razas disponibles usando Retrofit
    private fun configurarRazas() {
        citaViewModel.listRazas.observe(viewLifecycleOwner) { razas ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                razas
            )
            binding.atRaza.setAdapter(adapter)
        }

        citaViewModel.getRazas()
    }

    // Obtener los nuevos valores del formulario
    private fun guardarCambios() {
        if (guardando) return

        val nombreMascota =
            binding.etNombreMascota.text.toString().trim()

        val raza =
            binding.atRaza.text.toString().trim()

        val nombrePropietario =
            binding.etNombrePropietario.text.toString().trim()

        val telefono =
            binding.etTelefono.text.toString().trim()

        if (listOf(
                nombreMascota,
                raza,
                nombrePropietario,
                telefono
            ).any { it.isBlank() }
        ) {
            Toast.makeText(
                requireContext(),
                "Completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // copy conserva el ID y el síntoma original
        val citaEditada = cita.copy(
            nombreMascota = nombreMascota,
            raza = raza,
            nombrePropietario = nombrePropietario,
            telefono = telefono
        )

        cambiarEstadoGuardado(true)

        // Consultar una nueva imagen solamente si cambia la raza
        if (raza.equals(cita.raza, ignoreCase = true)) {
            actualizarCita(citaEditada)
        } else {
            citaViewModel.getImagenRaza(
                raza.lowercase(Locale.ROOT)
            ) { nuevaImagen ->

                if (_binding == null) return@getImagenRaza

                if (nuevaImagen.isBlank()) {
                    cambiarEstadoGuardado(false)

                    Toast.makeText(
                        requireContext(),
                        "No se pudo obtener la imagen de la raza",
                        Toast.LENGTH_LONG
                    ).show()

                    return@getImagenRaza
                }

                actualizarCita(
                    citaEditada.copy(urlImagen = nuevaImagen)
                )
            }
        }
    }

    // Actualizar el registro existente en Room
    private fun actualizarCita(citaActualizada: Cita) {
        citaViewModel.updateCita(citaActualizada) { exito ->

            if (_binding == null || !isAdded) {
                return@updateCita
            }

            cambiarEstadoGuardado(false)

            if (exito) {
                Toast.makeText(
                    requireContext(),
                    "Cita actualizada correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(
                    R.id.action_editarCitaFragment_to_homeFragment
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error al actualizar la cita",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Mostrar u ocultar la carga durante el guardado
    private fun cambiarEstadoGuardado(enCurso: Boolean) {
        guardando = enCurso

        binding.progress.isVisible = enCurso
        binding.svFormulario.isVisible = !enCurso

        val completos = listOf(
            binding.etNombreMascota.text.toString(),
            binding.atRaza.text.toString(),
            binding.etNombrePropietario.text.toString(),
            binding.etTelefono.text.toString()
        ).all { it.isNotBlank() }

        binding.btnEditarCita.isEnabled = completos && !enCurso
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
