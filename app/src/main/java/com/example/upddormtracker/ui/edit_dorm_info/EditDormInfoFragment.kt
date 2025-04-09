package com.example.upddormtracker.ui.edit_dorm_info

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentEditDormInfoBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditDormInfoFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val firestore = Firebase.firestore

    private var _binding: FragmentEditDormInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dormInfoViewModel =
            ViewModelProvider(this)[EditDormInfoViewModel::class.java]

        _binding = FragmentEditDormInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[EditDormInfoViewModel::class.java]

        // Access buttons using View Binding
        val saveButton: Button = binding.saveDormInfoButton
        val cancelButton: Button = binding.cancelDormInfoButton
        val inputCurfew: com.google.android.material.textfield.TextInputEditText =
            binding.inputCurfew
        val inputAddress: com.google.android.material.textfield.TextInputEditText =
            binding.inputAddress
        val inputEmail: com.google.android.material.textfield.TextInputEditText =
            binding.inputEmail
        val inputHistory: com.google.android.material.textfield.TextInputEditText =
            binding.inputHistory

        val inputCurfewLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputCurfewLayout
        val inputAddressLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputAddressLayout
        val inputEmailLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputEmailLayout
        val inputHistoryLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputHistoryLayout

        val userDorm = "molave" //replace with user's dorm



        firestore.collection("dorm-info")
            .whereEqualTo("dorm", userDorm)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Get the first matching document
                    val document = documents.documents[0]

                    inputCurfew.setText(document.getString("curfew"))
                    inputAddress.setText(document.getString("address"))
                    inputEmail.setText(document.getString("email"))
                    inputHistory.setText(document.getString("history"))

                    inputCurfew.doAfterTextChanged { text ->
                        viewModel.validateCurfew(text.toString().trim())
                    }
                    inputAddress.doAfterTextChanged { text ->
                        viewModel.validateAddress(text.toString().trim())
                    }
                    inputEmail.doAfterTextChanged { text ->
                        viewModel.validateContactDetails(text.toString().trim())
                    }
                    inputHistory.doAfterTextChanged { text ->
                        viewModel.validateHistory(text.toString().trim())
                    }

                    viewModel.curfewError.observe(viewLifecycleOwner) { error ->
                        inputCurfewLayout.error = error
                    }
                    viewModel.addressError.observe(viewLifecycleOwner) { error ->
                        inputAddressLayout.error = error
                    }
                    viewModel.contactDetailsError.observe(viewLifecycleOwner) { error ->
                        inputEmailLayout.error = error
                    }
                    viewModel.historyError.observe(viewLifecycleOwner) { error ->
                        inputHistoryLayout.error = error
                    }

                    saveButton.setOnClickListener{
                        viewModel.validateCurfew(inputCurfew.text.toString().trim())
                        viewModel.validateAddress(inputAddress.text.toString().trim())
                        viewModel.validateContactDetails(inputEmail.text.toString().trim())
                        viewModel.validateHistory(inputHistory.text.toString().trim())
                        if (viewModel.isFormValid()) {
                            val curfew = inputCurfew.text.toString().trim()
                            val address = inputAddress.text.toString().trim()
                            val email = inputEmail.text.toString().trim()
                            val history = inputHistory.text.toString().trim()
                            val itemToBeEdited = mapOf(
                                "curfew" to curfew,
                                "address" to address,
                                "email" to email,
                                "history" to history
                            )
                            editDormInfo(userDorm, itemToBeEdited)
                        }
                    }
                    cancelButton.setOnClickListener {
                        findNavController().navigateUp()
                    }

                }

            }
    }

    private fun editDormInfo(userDorm: String, itemToBeEdited: Map<String, String>){
        firestore.collection("dorm-info")
            .whereEqualTo("dorm", userDorm)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    // Get the first matching document
                    val document = it.documents[0]
                    document.reference.update(itemToBeEdited)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Dorm info edited successfully", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigateUp()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Error editing dorm info", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                else {
                    Toast.makeText(requireContext(), "Invalid dorm", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "Error fetching data from the database", Toast.LENGTH_SHORT)
                    .show()
            }

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}