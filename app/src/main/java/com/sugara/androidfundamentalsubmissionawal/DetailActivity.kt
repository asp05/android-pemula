package com.sugara.androidfundamentalsubmissionawal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity
import com.sugara.androidfundamentalsubmissionawal.data.response.Event
import com.sugara.androidfundamentalsubmissionawal.data.response.EventDetailResponse
import com.sugara.androidfundamentalsubmissionawal.data.retrofit.ApiConfig
import com.sugara.androidfundamentalsubmissionawal.ui.EventViewModel
import com.sugara.androidfundamentalsubmissionawal.ui.favorite.FavoriteViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var category : TextView
    private lateinit var name : TextView
    private lateinit var description : TextView
    private lateinit var ivPicture : ImageView
    private lateinit var quota : TextView
    private lateinit var time : TextView
    private lateinit var owner : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var btJoin : Button
    private lateinit var btnFavorite : FloatingActionButton

    private lateinit var detailViewModel: DetailViewModel

    private var eventLocal: EventsEntity? = null
    private var isFavorite: Boolean = false


    private fun observeEvent(id: Int) {
        detailViewModel.getEventById(id).observe(this) { event ->
            Log.d("DetailActivity", "cek observe detail view: $event")
            if (event != null) {
                isFavorite = true
                btnFavorite.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24))
            } else {
                isFavorite = false
                btnFavorite.setImageDrawable(getDrawable(R.drawable.baseline_favorite_border_24))
            }
            Log.d("DetailActivity", "event local saat di observe: $eventLocal")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        progressBar = findViewById(R.id.progressBar)
        category = findViewById(R.id.tvCategory)
        name = findViewById(R.id.tvTitle)
        description = findViewById(R.id.tvDescription)
        ivPicture = findViewById(R.id.ivPicture)
        quota = findViewById(R.id.tvQuota)
        time = findViewById(R.id.tvTime)
        owner = findViewById(R.id.tvOwner)
        btJoin = findViewById(R.id.btJoin)
        btnFavorite = findViewById(R.id.btnFavorite)

        detailViewModel = obtainViewModel(this)



        val id = intent.getIntExtra(EXTRA_ID, 0)
        findEvent(id)
        observeEvent(id)

        btnFavorite.setOnClickListener {
            Log.d("DetailActivity", "event local saat di click: $eventLocal")
            if (!isFavorite) {
                detailViewModel.insert(eventLocal!!)
                Toast.makeText(this, "Berhasil menambahkan ke favorite", Toast.LENGTH_SHORT).show()
            }else{
                detailViewModel.delete(eventLocal!!)
                Toast.makeText(this, "Berhasil menghapus dari favorite", Toast.LENGTH_SHORT).show()
            }
            observeEvent(id)
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun findEvent(id:Int) {
        showLoading(true)
        val client = ApiConfig.getApiService().getEventDetail(id)
        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(
                call: Call<EventDetailResponse>,
                response: Response<EventDetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setEvent(responseBody.event as Event)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setEvent(event: Event) {
        category.text = event.category
        name.text = event.name
        description.text = HtmlCompat.fromHtml(
            event.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this)
            .load(event.imageLogo)
            .into(ivPicture)
        val sisa_quota = event.quota?.minus(event.registrants!!) ?: 0
        quota.text = getString(R.string.sisa_kuota_orang, sisa_quota.toString())
        time.text = getString(R.string.waktu, event.beginTime, event.endTime)
        owner.text = getString(R.string.penyelenggara, event.ownerName)

        //masukan event local
        eventLocal = EventsEntity(
            event.id,
            event.name,
            event.imageLogo
        )

        btJoin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}