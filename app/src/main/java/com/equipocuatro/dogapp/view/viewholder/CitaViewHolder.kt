package com.equipocuatro.dogapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.ItemCitaBinding
import com.equipocuatro.dogapp.model.Cita
import android.graphics.BitmapFactory
import java.net.URL
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitaViewHolder(binding: ItemCitaBinding) : RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding

    fun setItemCita(cita: Cita, onClick: (Cita) -> Unit) {
        bindingItem.tvNombreMascota.text = cita.nombreMascota
        bindingItem.tvSintoma.text = cita.sintoma
        bindingItem.tvTurno.text = bindingItem.root.context.getString(R.string.turno, cita.id)
        bindingItem.root.setOnClickListener { onClick(cita) }
        bindingItem.ivMascota.clipToOutline = true
        bindingItem.ivMascota.setImageResource(R.drawable.img_perro)
        val imageUrl = cita.urlImagen
        if (imageUrl.startsWith("http", ignoreCase = true)) {
            val imageView = bindingItem.ivMascota
            imageView.tag = imageUrl
            (imageView.context as? androidx.lifecycle.LifecycleOwner)?.lifecycleScope?.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    runCatching { URL(imageUrl).openStream().use(BitmapFactory::decodeStream) }.getOrNull()
                }
                if (imageView.tag == imageUrl && bitmap != null) imageView.setImageBitmap(bitmap)
            }
        } else if (imageUrl.isNotBlank()) {
            val imageView = bindingItem.ivMascota
            val context = imageView.context
            val resourceId = context.resources.getIdentifier(imageUrl, "drawable", context.packageName)
            if (resourceId != 0) imageView.setImageResource(resourceId)
        }
    }
}
