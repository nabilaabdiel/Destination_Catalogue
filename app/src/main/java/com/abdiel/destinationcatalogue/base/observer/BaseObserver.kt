package com.abdiel.destinationcatalogue.base.observer

import com.abdiel.destinationcatalogue.api.ApiService
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.session.Session
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject

class BaseObserver(
    private val apiService: ApiService,
    private val session: Session,
) {
    operator fun invoke(
        block: suspend () -> String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO, toast: Boolean = false,
        responseListener: ApiObserver.ResponseListener
    ) {
        ApiObserver(block, responseListener = object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                responseListener.onSuccess(response)
            }

            override suspend fun onError(response: ApiResponse) {
//                responseListener.onError(response)
                ApiObserver({ apiService.renewToken() },
                    responseListener = object : ApiObserver.ResponseListener {
                        override suspend fun onSuccess(response: JSONObject) {
                            val token = response.getJSONObject("data")
                                .getString("token")

                            session.setValue(Const.TOKEN.PREF_TOKEN, token)
                            ApiObserver(block, responseListener = responseListener)
                        }

                        override suspend fun onError(response: ApiResponse) {
                            responseListener.onError(response)
                        }
                    })

            }

            override suspend fun onExpired(response: ApiResponse) {
                responseListener.onExpired(response)
            }
        })
    }
}
