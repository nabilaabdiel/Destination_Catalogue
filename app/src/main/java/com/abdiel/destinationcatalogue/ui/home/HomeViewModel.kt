package com.abdiel.destinationcatalogue.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.base.observer.BaseObserver
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.abdiel.destinationcatalogue.data.slider.ImageSlider
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.extension.toList
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
    ) : BaseViewModel() {

    private val _listAllDestination = MutableSharedFlow<List<Destination>>()
    val listAllDestination = _listAllDestination.asSharedFlow()

    private val _imageSlider = MutableSharedFlow<List<ImageSlider>>()
    val imageSlider = _imageSlider.asSharedFlow()

    fun allDestination() = viewModelScope.launch {
        baseObserver(
            block = { apiService.allDestinations() },
            toast = false,
            responseListener = object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<Destination>(gson)
                    _listAllDestination.emit(data)
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                }
            })
    }

    fun imageSlider() = viewModelScope.launch {
        baseObserver(
            { apiService.imageSlider()},Dispatchers.IO, false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<ImageSlider>(gson)
                    _imageSlider.emit(data)
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                }
            })
    }
}