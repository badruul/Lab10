package com.example.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab10.databinding.ActivityProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    // To get the reference to Firestore
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firestore = FirebaseFirestore.getInstance()
        // Null safety - We sure that userId has been passed
        val userId = intent.getStringExtra("userId")!!
        // Collection - like table in relational
        // Document - like row in relational

        // SELECT * FROM users WHERE id = userId
        // SELECT * FROM users (notes)
        firestore.collection("users")
            .document(userId).get().addOnCompleteListener{
                task ->
                if (task.isSuccessful){
                    val document = task.result
                    binding.nameTextView.text = document.get("name").toString()
                    binding.emailTextView.text = document.get("email").toString()
                    binding.phoneTextView.text = document.get("phone").toString()
                    binding.countryTextView.text = document.get("country").toString()
                    binding.cityTextView.text = document.get("city").toString()
                }
            }
    }
}