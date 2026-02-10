package com.garam.qook.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garam.qook.data.local.LocalGroceryData
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.repository.MainRepository
import kotlinx.coroutines.launch

class AnalysisViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {


    suspend fun deleteAnalysis(analysis: LocalRecipeAnalysis) = mainRepository.deleteAnalysis(analysis)
    fun saveGrocery(localGroceryData: LocalGroceryData) = viewModelScope.launch {
        mainRepository.saveGrocery(localGroceryData)
    }

}