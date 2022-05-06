package com.example.consumenoretrofit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.consumenoretrofit.data.local.MainDataSource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import com.example.consumenoretrofit.core.Result
import com.example.consumenoretrofit.data.models.Data
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(private val dataSource : MainDataSource):ViewModel() {
    fun fetchUsers() : StateFlow<Result<List<Data>>> = flow {
        kotlin.runCatching {
            dataSource.getInfo()
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )
}
class MainViewModelFactory(private val dataSource: MainDataSource) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MainDataSource::class.java).newInstance(dataSource)
    }

}