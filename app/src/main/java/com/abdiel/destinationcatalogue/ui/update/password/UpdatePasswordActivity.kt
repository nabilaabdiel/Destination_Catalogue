package com.abdiel.destinationcatalogue.ui.update.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.databinding.ActivityUpdatePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordActivity : BaseActivity<ActivityUpdatePasswordBinding, UpdatePasswordViewModel>
    (R.layout.activity_update_password) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}