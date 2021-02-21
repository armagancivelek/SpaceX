package com.armagancivelek.spacex.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.armagancivelek.spacex.R
import com.armagancivelek.spacex.adapter.SliderAdapter
import com.armagancivelek.spacex.databinding.FragmentDetailBinding
import com.armagancivelek.spacex.model.RocketResponse
import com.armagancivelek.spacex.viewmodel.NetworkResult
import com.armagancivelek.spacex.viewmodel.RocketViewModel
import kotlinx.coroutines.*


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val mViewModel: RocketViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var sliderAdapter: SliderAdapter
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var job: Job


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)


        CoroutineScope(Dispatchers.Default).launch {

            job = launch {
                delay(3000)
                withContext(Dispatchers.Main) {


                    observeLiveData()
                }
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (job.isActive)
            job.cancel()

    }

    private fun observeLiveData() {
        mViewModel.rocket.observe(viewLifecycleOwner, { response ->


            when (response) {
                is NetworkResult.Success -> {
                    hideProgressBar()
                    response.let { response ->


                        setDetail(response.data)


                    }
                }
                is NetworkResult.Error -> {
                    hideProgressBar()
                }
                is NetworkResult.Loading ->
                    showProgressBar()

            }
        })

    }

    fun init(view: View) {


        args.rocketId?.let { mViewModel.getOneRocket(it) }
        binding = FragmentDetailBinding.bind(view)
        sliderAdapter = SliderAdapter(arrayListOf())



        binding.sliderViewPager.apply {
            adapter = sliderAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

        }


    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setDetail(response: RocketResponse) {

        binding.animationView.visibility = View.INVISIBLE

        val imageList = response.flickr_images
        sliderAdapter.updateList(imageList as ArrayList<String>)
        binding.tvRocketName.text = response.rocket_name
        binding.status.text = if (response.active) "Status : Aktif" else "Status : Passive"
        binding.successRate.text = "Success rate pct: %${response.success_rate_pct}"
        binding.company.text = "Company : ${response.company}"
        binding.country.text = "Country : ${response.country}"
        binding.firstFlight.text = "First flight : ${response.first_flight}"
        binding.costPerLaunch.text = "Cost per launch : ${response.cost_per_launch} $"
        binding.description.text = response.description
        binding.diameter.text = "Diameter :${response.diameter.meters} m"
        binding.height.text = "Height : ${response.height.meters} m"

        binding.sliderIndicator.apply {

            setViewPager(binding.sliderViewPager)
        }


    }
}