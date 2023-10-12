package com.abdiel.destinationcatalogue.ui.register

import androidx.lifecycle.viewModelScope
import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.abdiel.destinationcatalogue.data.session.Session
import com.abdiel.destinationcatalogue.data.user.User
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val session: Session

): BaseViewModel() {

    private val _users = MutableSharedFlow<User>()
    val users = _users.asSharedFlow()

    fun register( username: String, email: String, phone: String, password: String, confirm_password: String )
    = viewModelScope.launch {
        _apiResponse.emit(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.register(username, email, phone, password, confirm_password) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.emit(ApiResponse().responseSuccess())
                }
            })
    }
}