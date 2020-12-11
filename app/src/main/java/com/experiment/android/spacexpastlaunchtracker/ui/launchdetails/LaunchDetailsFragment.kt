package com.experiment.android.spacexpastlaunchtracker.ui.launchdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.experiment.android.spacexpastlaunchtracker.databinding.FragmentLaunchDetailsBinding
import com.experiment.android.spacexpastlaunchtracker.models.ViewType
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.utils.custom.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

/**
 * A simple [Fragment] subclass.
 * Use the [LaunchDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LaunchDetailsFragment : Fragment(), ChipClickListener {

    private val args by navArgs<LaunchDetailsFragmentArgs>()

    /**
     * Inflate layout with databinding and set its lifecycleowner to PastLaunchesFragment to enable
     * databinding and to observe livedata
     */
    private var _binding: FragmentLaunchDetailsBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel as activityViewModel as activity is observing the LaunchDetailsViewModel to set screen title based on mission
     */
    private val viewModel: LaunchDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchDetailsData = args.dataModel

        populateValues(launchDetailsData)
        setupRecyclerView(launchDetailsData)

        setHasOptionsMenu(false)
    }

    private fun setupRecyclerView(launchDetailsData: PastLaunchResponse) {

        val launchDetailsAdapter =
            LaunchDataAdapter(splitData(launchDetailsData), this@LaunchDetailsFragment)

        binding.rcvLaunchDetails.apply {
            setHasFixedSize(true)
            val itemDecoration = CustomItemDecorator(15, 10)
            addItemDecoration(itemDecoration)

            adapter = launchDetailsAdapter
        }
    }

    /**
     * Split PastLaunchResponse to different viewtypes for recycler view
     */
    private fun splitData(pastLaunchDataModel: PastLaunchResponse): ArrayList<ViewType> {
        val splitList = ArrayList<ViewType>()
        splitList.addItem(pastLaunchDataModel.asMainDetailsModel())
        splitList.addItem(pastLaunchDataModel.asLinksModel())
        return splitList
    }

    private fun populateValues(launchDetailsData: PastLaunchResponse) {
        viewModel.setTitle(launchDetailsData.missionName)

        if (launchDetailsData.details == null || launchDetailsData.details == "") {
            binding.tvDescriptionLabel.isVisible = false
            binding.tvDescription.isVisible = false
        } else {
            binding.tvDescription.text = launchDetailsData.details
        }

        val videoId = launchDetailsData.links?.youtubeId
        if (null == videoId) {
            binding.youtubePlayer.isVisible = false
        } else {
            setUpYoutubeView(videoId)
        }
    }

    private fun setUpYoutubeView(videoId: String) {
        lifecycle.addObserver(binding.youtubePlayer)

        binding.youtubePlayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    override fun onChipClicked(url: String) {
        context?.let { launchExternalApp(it, url) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.youtubePlayer.release()
        _binding = null
    }
}