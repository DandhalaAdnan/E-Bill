package com.example.e_bill.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_bill.databinding.FragmentShowUserBinding


class ShowUserFragment : Fragment() {
    private lateinit var binding: FragmentShowUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShowUserBinding.inflate(layoutInflater)
        return binding.root
    }
}