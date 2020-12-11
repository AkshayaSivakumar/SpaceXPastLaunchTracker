package com.experiment.android.spacexpastlaunchtracker.ui.pastlaunches

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import com.experiment.android.spacexpastlaunchtracker.R
import com.experiment.android.spacexpastlaunchtracker.databinding.FragmentPastLaunchesBinding
import com.experiment.android.spacexpastlaunchtracker.utils.custom.CustomItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Entry point for dagger dependency injection
 */
@AndroidEntryPoint
class PastLaunchesFragment : Fragment(R.layout.fragment_past_launches) {

    private val viewModel by viewModels<PastLaunchesViewModel>()

    private var _binding: FragmentPastLaunchesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPastLaunchesBinding.bind(view)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initRecyclerViewAndObserveData()

        setHasOptionsMenu(true)
    }

    private fun initRecyclerViewAndObserveData() {

        val adapter =
            PastLaunchListPagingAdapter(PastLaunchListPagingAdapter.ListItemClickListener {
                viewModel.navigateToDetailsFragment(it)
            })

        binding.rcvLaunchList.apply {
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(CustomItemDecorator(25, 20))
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PastLaunchListLoadStateAdapter { adapter.retry() },
                footer = PastLaunchListLoadStateAdapter { adapter.retry() },
            )

            binding.layoutError.btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.pastLaunchList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        /**
         * Observe the recyclerview item click
         */
        viewModel.navigateToDetails.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(
                    PastLaunchesFragmentDirections.actionPastLaunchesFragmentToLaunchDetailsFragment(
                        it
                    )
                )
                viewModel.navigateToDetailsFragmentComplete()
            }
        }

        /**
         * Set recycler view and error layout visibilities based on the LoadingState
         */
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                binding.apply {
                    //Show progress bar when the state is loading
                    layoutError.containerCustomProgress.isVisible =
                        loadState.refresh is LoadState.Loading
                    //Show recycler view if the state when not loading
                    rcvLaunchList.isVisible = loadState.refresh is LoadState.NotLoading
                    //Show retry button if the status is error
                    layoutError.btnRetry.isVisible = loadState.refresh is LoadState.Error
                    //Show no network image view when the load state is error
                    layoutError.ivNoNetwork.isVisible = loadState.refresh is LoadState.Error
                    //Show msg/error textview when the status is loading or error and show the appropriate message
                    layoutError.tvMsgError.isVisible =
                        loadState.refresh is LoadState.Loading || loadState.refresh is LoadState.Error

                    if (loadState.refresh is LoadState.Loading) {
                        layoutError.tvMsgError.text = resources.getString(R.string.loading)
                    } else if (loadState.refresh is LoadState.Error) {
                        layoutError.tvMsgError.text = resources.getString(R.string.no_network_error)
                    }

                    if (loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached
                        && adapter.itemCount < 1
                    ) {
                        rcvLaunchList.isVisible = false
                        layoutError.tvEmptyError.isVisible = true
                    } else {
                        layoutError.tvEmptyError.isVisible = false
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}