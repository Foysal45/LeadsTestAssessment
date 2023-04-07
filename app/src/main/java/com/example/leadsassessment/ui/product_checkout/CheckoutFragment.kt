package com.example.leadsassessment.ui.product_checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.leadsassessment.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {
    private var binding: FragmentCheckoutBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentCheckoutBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}