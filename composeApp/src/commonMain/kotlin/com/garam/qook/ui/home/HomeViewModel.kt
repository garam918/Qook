package com.garam.qook.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garam.qook.data.AnalysisData
import com.garam.qook.data.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _analysisList = MutableStateFlow<List<AnalysisData>>(listOf())
    val analysisList = _analysisList.asStateFlow()

    init {

        viewModelScope.launch {

            repository.getAllData().collect {

                _analysisList.value = it

            }
        }
    }

    fun saveAnalysis(analysisData: AnalysisData) = viewModelScope.launch{
        repository.saveAnalysis(analysisData)
    }


}