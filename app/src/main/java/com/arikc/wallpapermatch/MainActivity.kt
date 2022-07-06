package com.arikc.wallpapermatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arikc.wallpapermatch.model.Photo
import com.arikc.wallpapermatch.service.UnsplashService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var currentPhotos: List<Photo>? = null
    private var recyclerView: RecyclerView? = null
    private var photoListViewAdapter: PhotoListViewAdapter? = null

    private var horizontalLayoutManager =
        LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView?.layoutManager = horizontalLayoutManager
        retrieveRandomPhotos()
    }

    private fun setAdapter() {
        photoListViewAdapter = currentPhotos?.let { PhotoListViewAdapter(it, this) }
        recyclerView?.adapter = photoListViewAdapter
    }

    private fun retrieveRandomPhotos() {
        val unsplashService = UnsplashService()
        CoroutineScope(
            Dispatchers.IO
        ).launch {
            try {
                currentPhotos = unsplashService.getRandomPhotos()
                Handler(Looper.getMainLooper()).post {
                    setAdapter()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("failed in main", it) }
            }
        }
    }
}