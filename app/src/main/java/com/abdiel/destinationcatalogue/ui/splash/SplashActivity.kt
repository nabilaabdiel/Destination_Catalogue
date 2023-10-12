package com.abdiel.destinationcatalogue.ui.splash

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.abdiel.destinationcatalogue.ui.home.HomeActivity
import com.abdiel.destinationcatalogue.ui.login.LoginActivity
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.databinding.ActivityMainBinding
import com.crocodic.core.extension.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivityMainBinding, SplashViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnLogin -> {
                viewModel.splash {
                    if (it) {
                        openActivity<HomeActivity>()
                    } else {
                        openActivity<LoginActivity>()
                    }
                    finish()
                }
            }
        }
        super.onClick(v)
    }
}