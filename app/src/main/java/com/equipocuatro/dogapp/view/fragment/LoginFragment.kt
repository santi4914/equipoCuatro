package com.equipocuatro.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.equipocuatro.dogapp.R
import com.equipocuatro.dogapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarBiometria()
        controladores()
    }

    private fun controladores() {
        binding.lottieHuella.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    // La libreria biometric construye la ventana emergente (y su boton Cancelar
    // la cierra); aqui solo se le dan el titulo, el subtitulo y el boton.
    private fun configurarBiometria() {
        val executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {})
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.tituloBiometria))
            .setSubtitle(getString(R.string.subtituloBiometria))
            .setNegativeButtonText(getString(R.string.cancelar))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()
    }
}
