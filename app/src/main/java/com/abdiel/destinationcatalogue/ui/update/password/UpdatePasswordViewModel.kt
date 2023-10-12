package com.abdiel.destinationcatalogue.ui.update.password

import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.base.viewModel.BaseViewModel
import com.abdiel.destinationcatalogue.data.session.Session
import com.google.gson.Gson
import javax.inject.Inject

class UpdatePasswordViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val session: Session
) : BaseViewModel() {

}