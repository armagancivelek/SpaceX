package com.armagancivelek.spacex.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.armagancivelek.spacex.R
import com.armagancivelek.spacex.adapter.RocketsAdapter
import com.armagancivelek.spacex.databinding.FragmentRocketsBinding
import com.armagancivelek.spacex.viewmodel.NetworkResult
import com.armagancivelek.spacex.viewmodel.RocketViewModel


class RocketsFragment : Fragment(R.layout.fragment_rockets) {

    private val mViewModel: RocketViewModel by viewModels()
    private lateinit var binding: FragmentRocketsBinding
    private lateinit var rocketsAdapter: RocketsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        observeLiveData()

    }


    private fun observeLiveData() {
        mViewModel.rockets.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressBar()
                    response.let {

                        rocketsAdapter.differ.submitList(response.data)

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

    private fun init(view: View) {
        val animation1: Animation = AnimationUtils.loadAnimation(
            context?.applicationContext,
            R.anim.fade
        )
        binding = FragmentRocketsBinding.bind(view)
        rocketsAdapter = RocketsAdapter()
        binding.rocketsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rocketsAdapter
            startAnimation(animation1)

        }


    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}