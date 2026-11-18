package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.FragmentDetalleCitaBinding
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.utils.Constants

class DetalleCitaFragment : Fragment() {
    private lateinit var binding: FragmentDetalleCitaBinding

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
        val cita = requireArguments().getSerializable(Constants.CLAVE_CITA) as Cita
        binding.tvInformacion.text = getString(
            R.string.informacionCita,
            cita.id,
            cita.nombreMascota,
            cita.raza,
            cita.nombrePropietario,
            cita.telefono,
            cita.sintoma
        )
        binding.ivMascota.clipToOutline = true
        Glide.with(binding.root.context).load(cita.urlImagen).into(binding.ivMascota)
    }
}
