package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.FragmentHomeBinding
import com.equipocuatro.dogapp.utils.Constants
import com.equipocuatro.dogapp.view.adapter.CitaAdapter
import com.equipocuatro.dogapp.viewmodel.CitaViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
    }

    private fun controladores() {
        binding.fabNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_nuevaCitaFragment)
        }
    }

    private fun observadorViewModel() {
        observerListCitas()
        observerProgress()
    }

    private fun observerListCitas() {
        citaViewModel.getListCitas()
        citaViewModel.listCitas.observe(viewLifecycleOwner) { listCitas ->
            binding.recyclerview.layoutManager = LinearLayoutManager(context)
            binding.recyclerview.adapter = CitaAdapter(listCitas) { cita ->
                val bundle = Bundle()
                bundle.putSerializable(Constants.CLAVE_CITA, cita)
                findNavController().navigate(R.id.action_homeFragment_to_detalleCitaFragment, bundle)
            }
        }
    }

    private fun observerProgress() {
        citaViewModel.progressState.observe(viewLifecycleOwner) { status ->
            binding.progress.isVisible = status
        }
    }
}
