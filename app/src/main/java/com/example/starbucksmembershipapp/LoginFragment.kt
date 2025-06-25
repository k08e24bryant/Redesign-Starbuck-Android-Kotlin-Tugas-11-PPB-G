package com.example.starbucksmembershipapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Session Management: Cek jika user sudah login
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return // Penting agar sisa kode tidak dieksekusi
        }

        val etEmail = view.findViewById<TextInputEditText>(R.id.et_email)
        val etPassword = view.findViewById<TextInputEditText>(R.id.et_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val tvGoToRegister = view.findViewById<TextView>(R.id.tv_go_to_register)

        tvGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}