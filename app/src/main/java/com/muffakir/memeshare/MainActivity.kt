package com.muffakir.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.muffakir.memeshare.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    var currenturl: String? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currenturl: String? = null

        loadmeme()

        binding.shareview.setOnClickListener{
            sharememe()
        }

        binding.refresh.setOnRefreshListener {
            loadmeme()
            binding.refresh.isRefreshing = false

        }

    }



fun sharememe() {

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_TEXT,
        "Hey I Got this meme from Muffakirs app Checkout meme here $currenturl"
    )
    val chooser = Intent.createChooser(intent, "Share Using.....")
    startActivity(chooser)

}

private fun loadmeme() {
    val queue = Volley.newRequestQueue(this)
    val url = "https://meme-api.com/gimme"
    binding.bar.visibility = View.VISIBLE

    val jsonrequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            currenturl = response.getString("url")
            Glide.with(this).load(currenturl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.bar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.bar.visibility = View.GONE
                    return false
                }


            }).into(binding.memeimage)

        },
        {
            Log.d("error", it.localizedMessage)
        })

// Add the request to the RequestQueue.
    queue.add(jsonrequest)

}}

