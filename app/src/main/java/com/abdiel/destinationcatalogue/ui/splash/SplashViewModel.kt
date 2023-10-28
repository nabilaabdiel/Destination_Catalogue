package com.abdiel.destinationcatalogue.ui.splash

import androidx.lifecycle.viewModelScope
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val session: Session) : BaseViewModel() {
    fun splash(done: (Boolean) -> Unit) = viewModelScope.launch {

        val userLogin = session.getString(Const.USER.PROFILE)

        done(userLogin == "Login")
    }
}