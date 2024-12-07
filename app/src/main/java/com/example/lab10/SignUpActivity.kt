package com.example.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab10.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root

        // Retrieve the connection to FirebaseAuth (connect to Firebase Auth)
        auth = FirebaseAuth.getInstance()

        // Retrieve the connection to Firestore (connect to Firebase Firestore)
        db = FirebaseFirestore.getInstance()

        setContentView(view)

        binding.signUpButton.setOnClickListener{
            val email = binding.regEmailEditText.text.toString()
            val password = binding.regPassword.text.toString()
            // auth -> createUser
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){

                        val newPerson = hashMapOf(
                            "name" to binding.regNameEditText.text.toString().trim(),
                            "city" to binding.regCityEditText.text.toString().trim(),
                            "country" to binding.regCountryEditText.text.toString().trim(),
                            "phone" to binding.regPhoneEditText.text.toString().trim(),
                            "email" to binding.regEmailEditText.text.toString().trim()
                        )

                        db.collection("users")
                            .document(auth.currentUser!!.uid)
                            .set(newPerson)

                        Snackbar.make(binding.root, "Successfully Registered." + " You may register now",
                            Snackbar.LENGTH_LONG).show()
                        finish()
                    }
                    else{
                        Snackbar.make(binding.root, "Registration Failed", Snackbar.LENGTH_LONG).show()
                    }
                }
        }

    }
}