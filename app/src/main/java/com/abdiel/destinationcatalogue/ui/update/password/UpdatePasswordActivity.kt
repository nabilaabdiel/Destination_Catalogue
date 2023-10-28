package com.abdiel.destinationcatalogue.ui.update.password

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.databinding.ActivityUpdatePasswordBinding
import com.abdiel.destinationcatalogue.ui.profile.ProfileActivity
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdatePasswordActivity :
    BaseActivity<ActivityUpdatePasswordBinding, UpdatePasswordViewModel>(R.layout.activity_update_password) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSave.setOnClickListener {
            if (binding.etPassword.isEmptyRequired(R.string.label_must_fill) ||
                binding.etNewPassword.isEmptyRequired(R.string.label_must_fill) ||
                binding.etConfirm.isEmptyRequired(R.string.label_must_fill)
            ) {
                return@setOnClickListener
            }

            val oldPassword = binding.etPassword.textOf()
            val newPassword = binding.etNewPassword.textOf()
            val confirmPassword = binding.etConfirm.textOf()

            viewModel.changePassword(oldPassword, newPassword, confirmPassword)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Loading")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<ProfileActivity>()
                                finishAffinity()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }
    }
}