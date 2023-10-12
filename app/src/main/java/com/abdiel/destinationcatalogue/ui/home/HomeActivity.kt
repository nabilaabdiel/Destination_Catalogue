package com.abdiel.destinationcatalogue.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.abdiel.destinationcatalogue.data.slider.ImageSlider
import com.abdiel.destinationcatalogue.databinding.ActivityHomeBinding
import com.abdiel.destinationcatalogue.databinding.ListDestinationBinding
import com.abdiel.destinationcatalogue.ui.destination.all.AllActivity
import com.abdiel.destinationcatalogue.ui.destination.children.ChildrenActivity
import com.abdiel.destinationcatalogue.ui.destination.nature.NatureActivity
import com.abdiel.destinationcatalogue.ui.detail.DetailActivity
import com.abdiel.destinationcatalogue.ui.profile.ProfileActivity
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.createIntent
import com.crocodic.core.extension.openActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    private var listDestinationHome = ArrayList<Destination?>()
    private var listAllDestinationHome = ArrayList<Destination?>()

    private val adapter by lazy {
        //Adapter
        CoreListAdapter<ListDestinationBinding, Destination>(R.layout.list_destination)
            .initItem(listDestinationHome) { _, data ->
                activityLauncher.launch(createIntent<DetailActivity> {
                    putExtra(Const.LIST.LIST_DESTINATION, data)
                })
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.cvProfile.setOnClickListener {
            activityLauncher.launch(createIntent<ProfileActivity>()) {
                getUser()
            }
        }

        getUser()
        viewModel.imageSlider()
        observe()

        binding.rvHome.adapter = adapter

        binding.cvChildren.setOnClickListener {
            openActivity<ChildrenActivity> {
            }
        }

        binding.cvNature.setOnClickListener {
            openActivity<NatureActivity> {
            }
        }

        binding.cvAll.setOnClickListener {
            openActivity<AllActivity> {
            }
        }

        //Search
        binding.svHome.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                listDestinationHome.clear()
                adapter.notifyDataSetChanged()
                listDestinationHome.addAll(listAllDestinationHome)
                adapter.notifyItemInserted(0)
            } else {
                val filter = listAllDestinationHome.filter { it?.name?.contains("$text", true) == true }
                listDestinationHome.clear()
                adapter.notifyDataSetChanged()
                listDestinationHome.addAll(filter)
                adapter.notifyItemInserted(0)
            }
            binding.tvEmpty.isVisible = listDestinationHome.isEmpty()
        }

        viewModel.allDestination()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.listAllDestination.collect { destination ->
                        listDestinationHome.clear()
                        listAllDestinationHome.clear()
                        adapter.notifyDataSetChanged()
                        listDestinationHome.addAll(destination)
                        listAllDestinationHome.addAll(destination)
                        adapter.notifyItemInserted(0)
                    }
                }

                launch {
                    viewModel.imageSlider.collect {
                        initSlider(it)
                    }
                }
            }
        }
//        viewModel.imageSlider
        //TODO: Slider Image nya belum selesai

    }

    private fun initSlider(data: List<ImageSlider>) {
        val imageList = ArrayList<SlideModel>()
        Log.d("slider", "tesImage: $data")
        data.forEach {
            imageList.add(SlideModel(it.photo, ScaleTypes.CENTER_CROP))
        }
        binding.ivSlider.setImageList(imageList)
    }

    private fun getUser() {
        val users = session.getUser()
        binding.user = users
    }
}