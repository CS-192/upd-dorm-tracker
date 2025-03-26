package com.example.upddormtracker.ui.late_night_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentLateNightPassBinding

class LateNightPassFragment : Fragment() {

    private var _binding: FragmentLateNightPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LateNightPassViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LateNightPassViewModel::class.java]
        _binding = FragmentLateNightPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get pass details from arguments or use sample data
        val passId = arguments?.getString("passId") ?: ""

        // Load pass details from ViewModel
        viewModel.loadPassDetails(passId)

        // Observe the pass details LiveData
        viewModel.passDetails.observe(viewLifecycleOwner) { details ->
            // Update UI with pass details
            binding.textDormer.text = details.dormer
            binding.textRoomNumber.text = details.roomNumber
            binding.textStudentNumber.text = details.studentNumber
            binding.textPassType.text = details.passType
            binding.textDestination.text = details.destination
            binding.textDepartureDate.text = details.departureDate
            binding.textArrivalDate.text = details.arrivalDate
            binding.textArrivalTime.text = details.arrivalTime
        }

        // Set click listeners for buttons
        binding.btnEditDetails.setOnClickListener {
            // Navigate to edit screen (to be implemented)
            Toast.makeText(requireContext(), "Edit details clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnDeleteRequest.setOnClickListener {
            // Show confirmation dialog and delete (to be implemented)
            Toast.makeText(requireContext(), "Delete request clicked", Toast.LENGTH_SHORT).show()

            // Call the delete method from ViewModel
            viewModel.deletePass(
                passId,
                onSuccess = {
                    // After successful deletion, navigate back
                    findNavController().navigateUp()
                },
                onError = { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}