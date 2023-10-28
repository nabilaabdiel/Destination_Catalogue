package com.abdiel.destinationcatalogue.ui.profile

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.databinding.ActivityProfileBinding
import com.abdiel.destinationcatalogue.ui.destination.favorite.FavoriteActivity
import com.abdiel.destinationcatalogue.ui.login.LoginActivity
import com.abdiel.destinationcatalogue.ui.update.password.UpdatePasswordActivity
import com.abdiel.destinationcatalogue.ui.update.profile.UpdateProfileActivity
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.createIntent
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getProfile() {
            getUser()
        }

        binding.btnEditProfile.setOnClickListener {
            activityLauncher.launch(createIntent<UpdateProfileActivity>()) {
                getUser()
                if (it.resultCode == 7) {
                    viewModel.getProfile() {
                        getUser()
                    }
                    observe()
                }
            }
        }

        binding.btnEditPassword.setOnClickListener {
            openActivity<UpdatePasswordActivity>()
        }

        binding.btnSave.setOnClickListener {
            openActivity<FavoriteActivity>()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect { logout ->
                        if (logout.status == ApiStatus.SUCCESS) {
                            openActivity<LoginActivity>()
                            finishAffinity()
                        }
                    }
                }
            }
        }
    }

    private fun getUser() {
        val users = session.getUser()
        binding.user = users
    }
}