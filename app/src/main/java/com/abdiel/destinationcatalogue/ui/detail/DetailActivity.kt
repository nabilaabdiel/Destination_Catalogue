package com.abdiel.destinationcatalogue.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.abdiel.destinationcatalogue.databinding.ActivityDetailBinding
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.snacked
import com.crocodic.core.helper.ImagePreviewHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {

    private var destination: Destination? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }

        destination = intent.getParcelableExtra(Const.LIST.LIST_DESTINATION)
        binding.data = destination

        binding.ivObject.setOnClickListener {
            ImagePreviewHelper(this).show(binding.ivObject, destination?.photo)
        }

        observe()

        initBtnSave()

        binding.btnUnsaved.setOnClickListener {
            viewModel.addBookmark(destination?.id)
            binding.root.snacked("Saved")

        }

        binding.btnSaved.setOnClickListener {
            viewModel.addBookmark(destination?.id)
            binding.root.snacked("Unsaved")
        }

        binding.btnRute.setOnClickListener {
            location()
        }

    }

    private fun location() {
        val intentUri = Uri.parse("google.navigation:q=${destination?.name}&mode=d")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        if (it.status == ApiStatus.SUCCESS) {
                            setResult(1)
                        }
                    }
                }
            }
        }
    }

    private fun initBtnSave() {
        if (destination?.favorite == true) {
            binding.btnUnsaved.visibility = View.GONE
            binding.btnSaved.visibility = View.VISIBLE
        } else {
            binding.btnUnsaved.visibility = View.VISIBLE
            binding.btnSaved.visibility = View.GONE
        }
    }
}
