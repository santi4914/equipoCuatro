package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.equipocuatro.dogapp.databinding.FragmentNuevaCitaBinding
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
        // La flecha rosada regresa al Home.
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observadorViewModel() {
        observerListRazas()
    }

    // Las razas llegan de la API y alimentan el AutoCompleteTextView.
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
