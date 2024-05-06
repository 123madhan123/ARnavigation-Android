package com.arnavigation.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnavigation.app.data.AuthRepository
import com.arnavigation.app.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    fun loginUser(email:String, password:String) = viewModelScope.launch {
        repository.login(email, password).collect {result ->
            when(result){
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Sign in success"))
                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _signInState.send(SignInState(isError = "Error"))
                }
            }
        }
    }
}


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val _signUpState = Channel<SignUpState>()
    val signInState = _signUpState.receiveAsFlow()

    fun registerUser(email:String, password:String) = viewModelScope.launch {
        repository.registerUser(email, password).collect {result ->
            when(result){
                is Resource.Success -> {
                    _signUpState.send(SignUpState(isSuccess = "Sign in success"))

                }
                is Resource.Loading -> {
                    _signUpState.send(SignUpState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(SignUpState(isError = "Error"))
                }
            }
        }
    }
}

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""

)