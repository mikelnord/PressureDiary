package com.example.pressurediary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.pressurediary.databinding.AddFragmentBinding
import com.example.pressurediary.model.PressDat

class AddFragment : Fragment() {
    private var _binding: AddFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddFragmentBinding.inflate(inflater, container, false)
        binding.buttonAdd.setOnClickListener {
            viewModel.addPressDat(
                PressDat(
                    viewModel.getDay(), viewModel.getMonth(),
                    binding.textHigh.editText?.text.toString(),
                    binding.textLow.editText?.text.toString(),
                    binding.textPuls.editText?.text.toString()
                )
            )
            it.findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}