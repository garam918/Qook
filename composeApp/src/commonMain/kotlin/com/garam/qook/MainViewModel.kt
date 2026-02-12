package com.garam.qook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garam.qook.auth.AuthRepository
import com.garam.qook.data.local.LocalUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<LocalUserData?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {

        viewModelScope.launch {
            _currentUser.value = authRepository.currentUser()

        }

    }

    suspend fun updateUser(user: LocalUserData) = authRepository.updateUserInfo(user)



    fun getLoggedIn() = authRepository.isLoggedIn()


}