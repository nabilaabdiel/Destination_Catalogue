package com.abdiel.destinationcatalogue.ui.destination.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.abdiel.destinationcatalogue.databinding.ActivityFavoriteBinding
import com.abdiel.destinationcatalogue.databinding.ListDestinationBinding
import com.abdiel.destinationcatalogue.ui.detail.DetailActivity
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.createIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteActivity : BaseActivity<ActivityFavoriteBinding, FavoriteViewModel>(R.layout.activity_favorite) {

    private var listDestinationHomeFav = ArrayList<Destination?>()
    private var listFavDestinationHome = ArrayList<Destination?>()

    private val adapter by lazy {
        //Adapter
        CoreListAdapter<ListDestinationBinding, Destination>(R.layout.list_destination)
            .initItem(listDestinationHomeFav) { _, data ->
                activityLauncher.launch(createIntent<DetailActivity> {
                    putExtra(Const.LIST.LIST_DESTINATION, data)
                })
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()

        binding.rvHome.adapter = adapter

        viewModel.listFavorite()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.listFavDestination.collect { destination ->
                        listDestinationHomeFav.clear()
                        listFavDestinationHome.clear()
                        adapter.notifyDataSetChanged()
                        listDestinationHomeFav.addAll(destination)
                        listFavDestinationHome.addAll(destination)
                        adapter.notifyItemInserted(0)
                    }
                }
            }
        }
    }
}