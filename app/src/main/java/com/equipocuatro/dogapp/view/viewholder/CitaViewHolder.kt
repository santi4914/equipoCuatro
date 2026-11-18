package com.equipocuatro.dogapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.ItemCitaBinding
import com.equipocuatro.dogapp.model.Cita

class CitaViewHolder(binding: ItemCitaBinding) : RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemCita(cita: Cita, onClick: (Cita) -> Unit) {
        bindingItem.tvNombreMascota.text = cita.nombreMascota
        bindingItem.tvSintoma.text = cita.sintoma
        bindingItem.tvTurno.text = bindingItem.root.context.getString(R.string.turno, cita.id)
        bindingItem.root.setOnClickListener { onClick(cita) }
        bindingItem.ivMascota.clipToOutline = true
        Glide.with(bindingItem.root.context).load(cita.urlImagen).into(bindingItem.ivMascota)
    }
}
