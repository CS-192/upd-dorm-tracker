package com.example.upddormtracker.ui.edit_faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentEditFaqBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditFaqFragment : Fragment() {
    private var _binding: FragmentEditFaqBinding? = null
    private val firestore = Firebase.firestore

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFaqBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun editFAQ(id: String, itemToBeEdited: Map<String, String>) {
        // Add document with auto-generated ID
        firestore.collection("faqs")
            .document(id)
            .update(itemToBeEdited)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "FAQ has been successfully edited!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error editing FAQ", Toast.LENGTH_SHORT)
                    .show()

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[EditFaqViewModel::class.java]

        // Access buttons using View Binding
        val saveButton: Button = binding.saveFaqButton
        val cancelButton: Button = binding.cancelFaqButton
        val inputQuestion: com.google.android.material.textfield.TextInputEditText =
            binding.inputQuestion
        val inputAnswer: com.google.android.material.textfield.TextInputEditText =
            binding.inputAnswer
        val inputQuestionLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputQuestionLayout
        val inputAnswerLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputAnswerLayout

        val args = EditFaqFragmentArgs.fromBundle(requireArguments())
        val id = args.id

        firestore.collection("faqs")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                inputQuestion.setText(result.getString("question"))
                inputAnswer.setText(result.getString("answer"))

                inputQuestion.doAfterTextChanged { text ->
                    viewModel.validateQuestion(text.toString().trim())
                }

                inputAnswer.doAfterTextChanged { text ->
                    viewModel.validateAnswer(text.toString().trim())
                }

                viewModel.questionError.observe(viewLifecycleOwner) { error ->
                    inputQuestionLayout.error = error
                }

                viewModel.answerError.observe(viewLifecycleOwner) { error ->
                    inputAnswerLayout.error = error
                }


                // Handle save button click
                saveButton.setOnClickListener {
                    viewModel.validateQuestion(inputQuestion.text.toString().trim())
                    viewModel.validateAnswer(inputAnswer.text.toString().trim())
                    if (viewModel.isFormValid()) {
                        val question = inputQuestion.text.toString().trim()
                        val answer = inputAnswer.text.toString().trim()
                        val itemToBeEdited = mapOf(
                            "question" to question,
                            "answer" to answer,
                        )
                        editFAQ(id, itemToBeEdited)
                    }
                }

                // Handle cancel button
                cancelButton.setOnClickListener {
                    // Handle cancel button click
                    findNavController().navigateUp()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}