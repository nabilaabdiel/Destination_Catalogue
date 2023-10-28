package com.abdiel.destinationcatalogue.data.constant

import java.io.File

object Const {

    object USER {
        const val USER_AUTH = "user_auth"
        const val PROFILE = "profile"
        const val PASSWORD = "password"
    }

    object TOKEN {
        const val PREF_TOKEN = "pref_token"
        const val DEVICE_TOKEN = "token"
    }

    object LIST {
        const val LIST_DESTINATION = "list_destination"
        const val SAVED_DESTINATION= 301
        const val UNSAVED_DESTINATION = 303
    }

    object TIMEOUT {
        const val NINETY_LONG = 90L
    }
}