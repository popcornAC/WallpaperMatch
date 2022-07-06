package com.arikc.wallpapermatch

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.arikc.wallpapermatch.service.UnsplashService
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max


class MainActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var constraintLayout: ConstraintLayout? = null
    private var currentPhotoURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        constraintLayout = findViewById(R.id.mainLayout)
        if (savedInstanceState?.get("currentPhotoURL") == null) {
            retrieveRandomPhoto()
        } else {
            Log.d("orientation", "We found a value in the instance state")
            currentPhotoURL = savedInstanceState.get("currentPhotoURL") as String?
            loadCurrentPhotoIntoImageView()
        }

    }

    private fun retrieveRandomPhoto() {
        val unsplashService = UnsplashService()
        CoroutineScope(
            Dispatchers.IO
        ).launch {
            try {
                currentPhotoURL = unsplashService.getRandomPhoto()
                Handler(Looper.getMainLooper()).post {
                    loadCurrentPhotoIntoImageView()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("failed in main", it) }
            }
        }
    }

    private fun loadCurrentPhotoIntoImageView() {
        Picasso.get().load(currentPhotoURL).into(imageView, object: Callback {
            override fun onSuccess() {
                val imageBitmap = (imageView?.drawable as BitmapDrawable).bitmap
                val imageDrawable = RoundedBitmapDrawableFactory.create(
                    resources, imageBitmap
                )
                imageDrawable.isCircular = true
                imageDrawable.cornerRadius =
                    max(imageBitmap.width, imageBitmap.height) / 15.0f
                imageView!!.setImageDrawable(imageDrawable)
            }

            override fun onError(e: java.lang.Exception?) {
                imageView?.setImageResource(R.drawable.ic_launcher_foreground)
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (currentPhotoURL != null) {
            outState.putString("currentPhotoURL", currentPhotoURL)
        }
    }
}