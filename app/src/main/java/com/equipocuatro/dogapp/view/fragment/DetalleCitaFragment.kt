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
        dataCita()
        controladores()
    }

    private fun dataCita() {
        cita = arguments?.getSerializable(Constants.CLAVE_CITA) as Cita
        binding.tvTitulo.text = cita.nombreMascota
        binding.tvTurno.text = getString(R.string.turno, cita.id)
        binding.tvRaza.text = cita.raza
        binding.tvSintoma.text = cita.sintoma
        binding.tvPropietario.text = getString(R.string.propietarioDetalle, cita.nombrePropietario)
        binding.tvTelefono.text = getString(R.string.telefonoDetalle, cita.telefono)
        Glide.with(binding.root.context).load(cita.urlImagen).into(binding.ivMascota)
    }

    private fun controladores() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detalleCitaFragment_to_homeFragment)
        }
        binding.fbEliminar.setOnClickListener {
            deleteCita()
        }
        binding.fbEditar.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Constants.CLAVE_CITA, cita)
            findNavController().navigate(R.id.action_detalleCitaFragment_to_editarCitaFragment, bundle)
        }
    }

    private fun deleteCita() {
        citaViewModel.deleteCita(cita) { eliminada ->
            if (eliminada) {
                findNavController().navigate(R.id.action_detalleCitaFragment_to_homeFragment)
            }
        }
    }
}
