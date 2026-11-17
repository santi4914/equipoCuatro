package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipocuatro.dogapp.databinding.FragmentDetalleCitaBinding
import com.equipocuatro.dogapp.model.Cita
import android.graphics.BitmapFactory
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

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
        val cita = requireArguments().getSerializable("cita") as Cita
        binding.tvInformacion.text = "Turno #${cita.id}\nMascota: ${cita.nombreMascota}\nRaza: ${cita.raza}\nPropietario: ${cita.nombrePropietario}\nTeléfono: ${cita.telefono}\nSíntoma: ${cita.sintoma}"
        binding.ivMascota.clipToOutline = true
        if (cita.urlImagen.startsWith("http", ignoreCase = true)) {
            viewLifecycleOwner.lifecycleScope.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    runCatching { URL(cita.urlImagen).openStream().use(BitmapFactory::decodeStream) }.getOrNull()
                }
                if (bitmap != null) binding.ivMascota.setImageBitmap(bitmap)
            }
        }
    }
}
