package com.example.scancard_demo_rv.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.scancard_demo_rv.R
import com.example.scancard_demo_rv.databinding.ChoiseFragmentBinding

class ChoiceFragment : Fragment(R.layout.choise_fragment) {

    private val binding: ChoiseFragmentBinding by viewBinding(ChoiseFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.payCardBtn.setOnClickListener {
            val action = ChoiceFragmentDirections.actionChoiceFragmentToPayCardFragment()
            findNavController().navigate(action)
        }
        binding.cardScanBtn.setOnClickListener {
            val action = ChoiceFragmentDirections.actionChoiceFragmentToCardScanFragment()
            findNavController().navigate(action)
        }
        binding.cardIoBtn.setOnClickListener {
            val action = ChoiceFragmentDirections.actionChoiceFragmentToCardIoFragment()
            findNavController().navigate(action)
        }
    }
}