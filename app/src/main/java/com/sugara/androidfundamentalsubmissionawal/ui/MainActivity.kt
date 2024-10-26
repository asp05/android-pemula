package com.sugara.androidfundamentalsubmissionawal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugara.androidfundamentalsubmissionawal.R
import com.sugara.androidfundamentalsubmissionawal.data.response.ListEventsItem
import com.sugara.androidfundamentalsubmissionawal.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[EventViewModel::class.java]
        mainViewModel.listEvents.observe(this) { listEvents ->
            setListEvents(listEvents)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvEvents.layoutManager = layoutManager
    }


    private fun setListEvents(listEvents: List<ListEventsItem>) {
        val adapter = EventsAdapter()
        adapter.submitList(listEvents)
        binding.rvEvents.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}