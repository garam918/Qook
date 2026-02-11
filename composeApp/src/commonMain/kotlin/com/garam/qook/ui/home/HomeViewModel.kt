package com.garam.qook.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garam.qook.data.RecipeAnalysis
import com.garam.qook.data.local.LocalGroceryData
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.data.remote.ApiRequest
import com.garam.qook.data.repository.MainRepository
import com.garam.qook.data.toUI
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<LocalUserData?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _analysisList = MutableStateFlow<List<RecipeAnalysis>>(listOf())
    val analysisList = _analysisList.asStateFlow()

    private val _groceryList = MutableStateFlow<List<LocalGroceryData>>(listOf())
    val groceryList = _groceryList.asStateFlow()

    init {

        viewModelScope.launch {

            _currentUser.value = repository.getCurrentUser()

            repository.getAllData(currentUser.value?.uid.toString()).collect {

                _analysisList.value = it.sortedByDescending { it.createdTime }.toUI()

            }

        }

        viewModelScope.launch {

            repository.getGrocery().collect {

                _groceryList.value = it.sortedByDescending { it.ingredientName }

            }
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        _currentUser.value = repository.getCurrentUser()
    }

    fun sendUrl(url: String) = viewModelScope.async {
        repository.sendUrl(ApiRequest(url))
    }

    fun saveAnalysis(recipeAnalysis: RecipeAnalysis) = viewModelScope.launch{
        repository.saveAnalysis(recipeAnalysis)
    }

    fun signOut() = viewModelScope.launch {
        repository.signOut()
    }

    fun deleteAccount() = viewModelScope.launch {
        repository.deleteAccount()
    }

    fun updateUser(user: LocalUserData) = viewModelScope.launch {
        repository.updateUserData(user)
        _currentUser.update {
            user
        }
    }


}