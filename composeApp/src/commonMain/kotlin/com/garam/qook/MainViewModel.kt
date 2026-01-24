package com.garam.qook

import androidx.lifecycle.ViewModel
import com.garam.qook.auth.AuthRepository

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {


    val currentUser = authRepository.currentUser()




}