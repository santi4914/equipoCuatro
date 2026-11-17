package com.equipocuatro.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.equipocuatro.dogapp.databinding.ItemCitaBinding
import com.equipocuatro.dogapp.model.Cita
import com.equipocuatro.dogapp.view.viewholder.CitaViewHolder

class CitaAdapter(
    private val listCitas: MutableList<Cita>,
    private val onClick: (Cita) -> Unit
) :
    RecyclerView.Adapter<CitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCitas.size
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listCitas[position]
        holder.setItemCita(cita, onClick)
    }
}
