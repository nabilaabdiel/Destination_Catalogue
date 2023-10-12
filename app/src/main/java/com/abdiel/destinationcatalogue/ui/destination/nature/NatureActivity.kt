package com.abdiel.destinationcatalogue.ui.destination.nature

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.abdiel.destinationcatalogue.databinding.ActivityNatureBinding
import com.abdiel.destinationcatalogue.databinding.ListDestinationBinding
import com.abdiel.destinationcatalogue.ui.detail.DetailActivity
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.createIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NatureActivity : BaseActivity<ActivityNatureBinding, NatureViewModel>(R.layout.activity_nature) {

    private var listDestinationHomeNature = ArrayList<Destination?>()
    private var listNatureDestinationHome = ArrayList<Destination?>()

    private val adapter by lazy {
        //Adapter
        CoreListAdapter<ListDestinationBinding, Destination>(R.layout.list_destination)
            .initItem(listDestinationHomeNature) { _, data ->
                activityLauncher.launch(createIntent<DetailActivity> {
                    putExtra(Const.LIST.LIST_DESTINATION, data)
                })
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()

        binding.rvHome.adapter = adapter

        viewModel.natureDestinations()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.listNatureDestination.collect { destination ->
                        listDestinationHomeNature.clear()
                        listNatureDestinationHome.clear()
                        adapter.notifyDataSetChanged()
                        listDestinationHomeNature.addAll(destination)
                        listNatureDestinationHome.addAll(destination)
                        adapter.notifyItemInserted(0)
                    }
                }
            }
        }
    }
}