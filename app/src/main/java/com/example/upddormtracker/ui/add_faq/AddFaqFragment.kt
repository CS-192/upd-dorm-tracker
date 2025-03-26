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

class AddFaqFragment : Fragment() {
    private var _binding: FragmentAddFaqBinding? = null

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
            viewModel.validateQuestion(text.toString())
        }

        inputAnswer.doAfterTextChanged { text ->
            viewModel.validateAnswer(text.toString())
        }

        viewModel.questionError.observe(viewLifecycleOwner) { error ->
            inputQuestionLayout.error = error
        }

        viewModel.answerError.observe(viewLifecycleOwner) { error ->
            inputAnswerLayout.error = error
        }


        // Handle save button click
        saveButton.setOnClickListener {
            viewModel.validateQuestion(inputQuestion.text.toString())
            viewModel.validateAnswer(inputAnswer.text.toString())
            if (viewModel.isFormValid()) {
                Toast.makeText(requireContext(), "FAQ added successfully!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

        // Handle cancel button
        cancelButton.setOnClickListener {
            // Handle cancel button click
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}