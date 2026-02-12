package com.garam.qook.ui.groceryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garam.qook.data.local.LocalGroceryData
import com.garam.qook.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class GroceryViewModel(
    private val repository: MainRepository
): ViewModel() {


    private val _groceryList = MutableStateFlow<List<LocalGroceryData>>(listOf())
    val groceryList = _groceryList.asStateFlow()

    init {

        viewModelScope.launch {

            repository.getGrocery().collect {

                _groceryList.value = it.sortedByDescending { it.ingredientName }

            }

        }
    }

    fun deleteGrocery(groceryData: LocalGroceryData) = viewModelScope.launch {
        repository.deleteGrocery(groceryData.id)
    }

}