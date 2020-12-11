package com.experiment.android.spacexpastlaunchtracker.ui.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.experiment.android.spacexpastlaunchtracker.R
import com.experiment.android.spacexpastlaunchtracker.databinding.ActivityBaseBinding
import com.experiment.android.spacexpastlaunchtracker.ui.launchdetails.LaunchDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBaseBinding
    private val viewModel: LaunchDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()

        setSupportActionBar(binding.toolbar)

        setupNavigationController()

        setupAppBarConfiguration()

        observeViewModel()
    }

    /**
     * Setup Binding
     */
    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
    }

    /**
     * Setup navigation controller
     */
    private fun setupNavigationController() {
        navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
            title = when (destination.id) {
                R.id.pastLaunchesFragment -> resources.getString(R.string.fragment_name_past_launches)
                R.id.launchDetailsFragment -> resources.getString(R.string.fragment_name_launch_details)
                R.id.aboutFragment -> resources.getString(R.string.fragment_name_about)
                else -> resources.getString(R.string.fragment_name_past_launches)
            }
        }
    }

    /**
     * Setup appbar configuration
     */
    private fun setupAppBarConfiguration() {
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Observe LaunchDetails fragment and set the mission name as the screen title for LaunchDetails fragment
     */
    private fun observeViewModel() {
        viewModel.missionTitle.observe(this, Observer {
            setTitle(it)
        })
    }

    /**
     * Method to set screen title
     */
    private fun setTitle(title: String) {
        binding.toolbar.title = title
    }

}