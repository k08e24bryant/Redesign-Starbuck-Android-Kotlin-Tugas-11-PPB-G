package com.example.StarbucksMembershipApp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.starbucksmembershipapp.R

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        val btnRegister = view.findViewById<Button>(R.id.btn_register)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        val userId = auth.currentUser!!.uid
                        val userMap = hashMapOf(
                            "email" to email,
                            "stars" to 0
                        )
                        firestore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    } else {
                        Toast.makeText(context, "Registrasi Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}