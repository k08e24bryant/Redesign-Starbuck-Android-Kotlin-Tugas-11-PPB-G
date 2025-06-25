package com.example.starbucksmembershipapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val tvMemberName = view.findViewById<TextView>(R.id.tv_member_email)
        val tvStarBalance = view.findViewById<TextView>(R.id.tv_star_balance)
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)

        // Cek jika user tidak login, tendang ke login screen
        if (auth.currentUser == null) {
            findNavController().navigate(R.id.action_global_to_loginFragment)
            return
        }

        loadUserData(tvMemberName, tvStarBalance)

        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_global_to_loginFragment)
        }
    }

    private fun loadUserData(tvMemberEmail: TextView, tvStarBalance: TextView) {
        val userId = auth.currentUser!!.uid
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val email = document.getString("email")
                    val stars = document.getLong("stars")

                    // Mengatur teks ke TextView
                    tvMemberEmail.text = email
                    tvStarBalance.text = "$stars ★"
                } else {
                    Toast.makeText(context, "Data user tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Gagal memuat data: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}