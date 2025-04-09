package com.example.upddormtracker.ui.add_faq

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
import com.example.upddormtracker.databinding.FragmentAddFaqBinding
import com.example.upddormtracker.datamodel.FAQ
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddFaqFragment : Fragment() {
    private var _binding: FragmentAddFaqBinding? = null
    private val firestore = Firebase.firestore

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addFaqViewModel =
            ViewModelProvider(this)[AddFaqViewModel::class.java]
        _binding = FragmentAddFaqBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun addFAQ(faq: FAQ){
        // Add document with auto-generated ID
        firestore.collection("faqs")
            .add(faq)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "FAQ has been successfully posted!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "Error adding FAQ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[AddFaqViewModel::class.java]

        // Access buttons using View Binding
        val saveButton: Button = binding.saveFaqButton
        val cancelButton: Button = binding.cancelFaqButton
        val inputQuestion: com.google.android.material.textfield.TextInputEditText = binding.inputQuestion
        val inputAnswer: com.google.android.material.textfield.TextInputEditText = binding.inputAnswer
        val inputQuestionLayout: com.google.android.material.textfield.TextInputLayout = binding.inputQuestionLayout
        val inputAnswerLayout: com.google.android.material.textfield.TextInputLayout = binding.inputAnswerLayout

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

        saveButton.setOnClickListener {
            viewModel.validateQuestion(inputQuestion.text.toString().trim())
            viewModel.validateAnswer(inputAnswer.text.toString().trim())
            if (viewModel.isFormValid()) {
                val question = inputQuestion.text.toString().trim()
                val answer = inputAnswer.text.toString().trim()
                val item = FAQ(
                    "",
                    question,
                    answer,
                    "Molave"
                )
                addFAQ(item)
            }
        }

        cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}