package com.example.e_bill.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val loginDataSource: LoginDataSource) : ViewModel() {
    private val _showSplash = MutableStateFlow(true)
    val isLoading = _showSplash.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _showSplash.value = false
        }
    }

    val loginSuccess = MutableLiveData<Boolean>()
    val loginFailedMessage = MutableLiveData<String?>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginDataSource.login(email, password, object : LoginDataSource.LoginCallBack {
                override fun onSuccess() {
                    loginSuccess.postValue(true)
                }

                override fun onError(message: String?) {
                    loginSuccess.postValue(false)
                    loginFailedMessage.postValue(message)
                }
            })
        }

    }
}