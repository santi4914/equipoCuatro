package com.equipocuatro.dogapp.view

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        aplicarMargenesDelSistema()
    }

    // Desde Android 15 la app se dibuja de borde a borde: se deja espacio
    // para la barra de estado y la de navegacion. La franja de la barra de
    // estado queda gris oscura (como los encabezados de todas las pantallas)
    // y la de navegacion toma el fondo del contenedor.
    private fun aplicarMargenesDelSistema() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { vista, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            vista.setPadding(barras.left, barras.top, barras.right, 0)
            binding.navigationContainer.setPadding(0, 0, 0, barras.bottom)
            insets
        }
    }
}
