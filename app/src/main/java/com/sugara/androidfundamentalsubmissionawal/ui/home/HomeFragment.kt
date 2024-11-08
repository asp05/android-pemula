package com.sugara.androidfundamentalsubmissionawal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugara.androidfundamentalsubmissionawal.data.response.ListEventsItem
import com.sugara.androidfundamentalsubmissionawal.databinding.FragmentHomeBinding
import com.sugara.androidfundamentalsubmissionawal.ui.EventHorizontalAdapter
import com.sugara.androidfundamentalsubmissionawal.ui.EventsAdapter
import com.sugara.androidfundamentalsubmissionawal.ui.upcoming.UpcomingViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        homeViewModel.listEvents.observe(viewLifecycleOwner) { listEvents ->
            setListEvents(listEvents)
        }

        homeViewModel.listEventsFinished.observe(viewLifecycleOwner) { listEvents ->
            setListEventsFinished(listEvents)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvEvents.layoutManager = layoutManager

        val layoutManagerFinished = LinearLayoutManager(requireContext())
        binding.rvEventsFinished.layoutManager = layoutManagerFinished
    }

    private fun setListEvents(listEvents: List<ListEventsItem>) {
        val adapter = EventHorizontalAdapter()
        adapter.submitList(listEvents)
        binding.rvEvents.adapter = adapter

    }

    private fun setListEventsFinished(listEvents: List<ListEventsItem>) {
        val adapter = EventsAdapter()
        adapter.submitList(listEvents)
        binding.rvEventsFinished.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}