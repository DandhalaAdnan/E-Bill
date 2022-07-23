package com.example.e_bill.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory constructor(private val loginDataSource: LoginDataSource) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.loginDataSource) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}