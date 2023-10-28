package com.abdiel.destinationcatalogue.ui.detail

import androidx.lifecycle.viewModelScope
import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiService: ApiService
    ) : BaseViewModel() {

//    private val _addBookmark = MutableSharedFlow<List<Destination>>() // private mutable shared flow
//    val addBookmark = _addBookmark.asSharedFlow() // publicly exposed as read-only shared flow

    fun addBookmark(destination_id: Int?) = viewModelScope.launch {
        ApiObserver(
            { apiService.addBookmark(destination_id) }, false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.emit(ApiResponse().responseSuccess("save success"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                }
            })
    }

    fun removeBookmark(destination_id: Int?) = viewModelScope.launch {
        ApiObserver(
            { apiService.removeBookmark(destination_id) }, false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.emit(ApiResponse().responseSuccess("remove success"))
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                }
            })
    }
}