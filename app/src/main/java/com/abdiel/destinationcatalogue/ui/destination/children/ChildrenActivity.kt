package com.abdiel.destinationcatalogue.ui.destination.children

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abdiel.destinationcatalogue.R
import com.abdiel.destinationcatalogue.base.activity.BaseActivity
import com.abdiel.destinationcatalogue.data.constant.Const
import com.abdiel.destinationcatalogue.data.destination.Destination
import com.abdiel.destinationcatalogue.databinding.ActivityAllBinding
import com.abdiel.destinationcatalogue.databinding.ActivityChildrenBinding
import com.abdiel.destinationcatalogue.databinding.ListDestinationBinding
import com.abdiel.destinationcatalogue.ui.destination.all.AllViewModel
import com.abdiel.destinationcatalogue.ui.detail.DetailActivity
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.createIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChildrenActivity : BaseActivity<ActivityChildrenBinding, ChildrenViewModel>(R.layout.activity_children) {

    private var listDestinationHomeChildren = ArrayList<Destination?>()
    private var listChildrenDestinationHome = ArrayList<Destination?>()

    private val adapter by lazy {
        //Adapter
        CoreListAdapter<ListDestinationBinding, Destination>(R.layout.list_destination)
            .initItem(listDestinationHomeChildren) { _, data ->
                activityLauncher.launch(createIntent<DetailActivity> {
                    putExtra(Const.LIST.LIST_DESTINATION, data)
                })
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()

        binding.rvHome.adapter = adapter

        viewModel.childrenDestinations()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.listChildrenDestination.collect { destination ->
                        listDestinationHomeChildren.clear()
                        listChildrenDestinationHome.clear()
                        adapter.notifyDataSetChanged()
                        listDestinationHomeChildren.addAll(destination)
                        listChildrenDestinationHome.addAll(destination)
                        adapter.notifyItemInserted(0)
                    }
                }
            }
        }
    }
}