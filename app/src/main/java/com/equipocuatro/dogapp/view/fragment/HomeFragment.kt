package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipocuatro.dogapp.databinding.FragmentHomeBinding
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
        observadorViewModel()
    }

    private fun observadorViewModel() {
        observerListCitas()
        observerProgress()
    }

    // La lista sale de la base de datos local (Room) por medio del ViewModel.
    private fun observerListCitas() {
        citaViewModel.getListCitas()
        citaViewModel.listCitas.observe(viewLifecycleOwner) { listCitas ->
            binding.recyclerview.layoutManager = LinearLayoutManager(context)
            binding.recyclerview.adapter = CitaAdapter(listCitas)
        }
    }

    private fun observerProgress() {
        citaViewModel.progressState.observe(viewLifecycleOwner) { status ->
            binding.progress.isVisible = status
        }
    }
}
