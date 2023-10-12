package com.abdiel.destinationcatalogue.ui.destination.all

import androidx.lifecycle.viewModelScope
import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.extension.toList
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
) : BaseViewModel() {

    private val _listAllDestination = MutableSharedFlow<List<Destination>>()
    val listAllDestination = _listAllDestination.asSharedFlow()

    fun allDestination() = viewModelScope.launch {
        ApiObserver(
            { apiService.allDestinations() }, false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<Destination>(gson)
                    _listAllDestination.emit(data)
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                }
            })
    }
}