package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.FragmentDetalleCitaBinding
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.utils.Constants
import com.equipocuatro.dogapp.viewmodel.CitaViewModel

class DetalleCitaFragment : Fragment() {
    private lateinit var binding: FragmentDetalleCitaBinding
    private val citaViewModel: CitaViewModel by viewModels()
    private lateinit var cita: Cita

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalleCitaBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cita = requireArguments().getSerializable(Constants.CLAVE_CITA) as Cita
        mostrarCita()
        controladores()
    }

    private fun mostrarCita() {
        binding.tvTitulo.text = cita.nombreMascota
        binding.tvTurno.text = getString(R.string.turno, cita.id)
        binding.tvRaza.text = cita.raza
        binding.tvSintoma.text = cita.sintoma
        binding.tvPropietario.text = getString(R.string.propietarioDetalle, cita.nombrePropietario)
        binding.tvTelefono.text = getString(R.string.telefonoDetalle, cita.telefono)
        Glide.with(binding.root.context).load(cita.urlImagen).into(binding.ivMascota)
    }

    private fun controladores() {
        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_detalleCitaFragment_to_homeFragment)
        }
        binding.fbEliminar.setOnClickListener {
            binding.fbEliminar.isEnabled = false
            binding.fbEditar.isEnabled = false
            binding.btnVolver.isEnabled = false
            citaViewModel.deleteCita(cita) { deleted ->
                if (deleted && isAdded) {
                    findNavController().navigate(R.id.action_detalleCitaFragment_to_homeFragment)
                } else if (isAdded) {
                    binding.fbEliminar.isEnabled = true
                    binding.fbEditar.isEnabled = true
                    binding.btnVolver.isEnabled = true
                }
            }
        }
        binding.fbEditar.setOnClickListener {
            val args = Bundle().apply { putSerializable(Constants.CLAVE_CITA, cita) }
            findNavController().navigate(
                R.id.action_detalleCitaFragment_to_editarCitaFragment,
                args
            )
        }
    }
}
