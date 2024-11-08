package com.sugara.androidfundamentalsubmissionawal.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugara.androidfundamentalsubmissionawal.data.response.ListEventsItem
import com.sugara.androidfundamentalsubmissionawal.databinding.FragmentFavoriteBinding
import com.sugara.androidfundamentalsubmissionawal.ui.EventsAdapter


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        mainViewModel.getAllEvents().observe(viewLifecycleOwner) { listEvents ->
            if (listEvents != null) {
                // change from EventsEntity to ListEventsItem
                val listEventItems = listEvents.map {
                    ListEventsItem(
                        id = it.id,
                        name = it.name,
                        imageLogo = it.imageLogo,
                    )
                }
                setListEvents(listEventItems)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.layoutManager = layoutManager
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setListEvents(listEvents: List<ListEventsItem>) {
        val adapter = EventsAdapter()
        adapter.submitList(listEvents)
        binding.rvEvents.adapter = adapter

    }


}