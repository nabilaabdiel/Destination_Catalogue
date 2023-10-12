package com.abdiel.destinationcatalogue.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.databinding.ActivityRegisterBinding
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.snacked
import com.crocodic.core.extension.textOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnRegister.setOnClickListener {
            if (binding.etUsername.isEmptyRequired(R.string.label_must_fill) ||
                binding.etPhoneNumber.isEmptyRequired(R.string.label_must_fill) ||
                binding.etPassword.isEmptyRequired(R.string.label_must_fill) ||
                binding.etConfirm.isEmptyRequired(R.string.label_must_fill))
            {
                return@setOnClickListener
            }

            val username = binding.etUsername.textOf()
            val email = binding.etEmail.textOf()
            val phone = binding.etPhoneNumber.textOf()
            val password = binding.etPassword.textOf()
            val passwordConfirmation = binding.etConfirm.textOf()

            if (username.length < 3) {
                binding.root.snacked("name minimum 3 characters")

                return@setOnClickListener
            }

            if (password.length < 8 && passwordConfirmation.length < 8) {
                binding.root.snacked("Password minimum 8 characters")

                return@setOnClickListener
            }

            if (password.length > 20 && passwordConfirmation.length < 8) {
                binding.root.snacked("Password maximum 20 characters")

                return@setOnClickListener
            }

            viewModel.register(username, email, phone, password, passwordConfirmation)
        }

        binding.btnLogin.setOnClickListener{
            finish()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Loading...")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }
    }
}