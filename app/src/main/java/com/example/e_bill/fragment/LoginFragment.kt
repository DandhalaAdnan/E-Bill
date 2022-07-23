package com.example.e_bill.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.e_bill.R
import com.example.e_bill.databinding.FragmentLoginBinding
import com.example.e_bill.viewModel.LoginDataSource
import com.example.e_bill.viewModel.LoginViewModelFactory
import com.example.e_bill.viewModel.MainViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(LoginDataSource(requireContext()))
        )[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginSuccess.observe(requireActivity()) {
            if (it) {
                findNavController().popBackStack()
                findNavController().navigate(R.id.searchUserFragment)
                binding.pbLoginLoading.visibility = View.GONE
                binding.btnLogin.visibility = View.GONE
                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                binding.etEmailLogin.text?.clear()
                binding.etPasswordLogin.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Invalid Email & Password", Toast.LENGTH_SHORT)
                    .show()
                binding.pbLoginLoading.visibility = View.GONE
                binding.btnLogin.visibility = View.VISIBLE
            }
        }
        binding.btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.etEmailLogin.text.toString()) && TextUtils.isEmpty(binding.etPasswordLogin.text.toString())) {
                binding.tvValidEmail.visibility = View.VISIBLE
                binding.tvValidPassword.visibility = View.VISIBLE
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailLogin.text.toString())
                    .matches()
            ) {
                binding.tvValidEmail.visibility = View.VISIBLE
                binding.tvValidPassword.visibility = View.GONE
            } else if (TextUtils.isEmpty(binding.etPasswordLogin.text.toString())) {
                binding.tvValidPassword.visibility = View.VISIBLE
                binding.tvValidEmail.visibility = View.GONE
            } else{
                binding.btnLogin.visibility = View.GONE
                binding.tvValidEmail.visibility = View.GONE
                binding.tvValidPassword.visibility = View.GONE
                binding.pbLoginLoading.visibility = View.VISIBLE
                checkLoginCredentials(
                    binding.etEmailLogin.text.toString(),
                    binding.etPasswordLogin.text.toString()
                )
            }
        }
        binding.etEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvValidEmail.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvValidPassword.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun checkLoginCredentials(email: String, password: String) {
        viewModel.login(email, password)
    }
}
