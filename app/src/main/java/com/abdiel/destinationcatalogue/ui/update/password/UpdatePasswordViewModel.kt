package com.abdiel.destinationcatalogue.ui.update.password

import androidx.lifecycle.viewModelScope
import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val apiService: ApiService
) : BaseViewModel() {

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) =
        viewModelScope.launch {
            _apiResponse.emit(ApiResponse().responseLoading())
            ApiObserver({ apiService.changePassword(oldPassword, newPassword, confirmPassword) },
                false,
                object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val message = response.getString(ApiCode.MESSAGE)
                        _apiResponse.emit(ApiResponse().responseSuccess(message))
                    }
                })
        }
}