package com.abdiel.destinationcatalogue.base.viewModel

import com.abdiel.destinationcatalogue.base.observer.BaseObserver
import com.crocodic.core.base.viewmodel.CoreViewModel
import javax.inject.Inject

open class BaseViewModel: CoreViewModel() {
    override fun apiLogout() {
    }

    override fun apiRenewToken(){

    }

    @Inject
    lateinit var baseObserver: BaseObserver
}