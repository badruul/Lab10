package com.example.lab10

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab10.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, ProfileActivity::class.java)
                    // To get the user ID
                    val userId = auth.currentUser!!.uid
                    intent.putExtra("userId", userId)
                    startActivity(intent)

                }
                else {
                    Snackbar.make(binding.root, "Login Failed", Snackbar.LENGTH_LONG).show()

                }
            }




        }
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}