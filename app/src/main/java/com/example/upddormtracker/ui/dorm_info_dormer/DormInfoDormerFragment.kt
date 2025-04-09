package com.example.upddormtracker.ui.dorm_info_dormer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.databinding.FragmentDormInfoDormerBinding

class DormInfoDormerFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var _binding: FragmentDormInfoDormerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dormInfoViewModel =
            ViewModelProvider(this)[DormInfoDormerViewModel::class.java]

        _binding = FragmentDormInfoDormerBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}