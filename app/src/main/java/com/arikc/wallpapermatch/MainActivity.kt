package com.arikc.wallpapermatch

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.arikc.wallpapermatch.service.UnsplashService
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.max


class MainActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        logRandomPhoto()
    }

    private fun logRandomPhoto() {
        val unsplashService = UnsplashService()
        GlobalScope.launch {
            try {
                val photo = unsplashService.getRandomPhoto()
                Handler(Looper.getMainLooper()).post {
                    Picasso.get().load(photo).into(imageView, object: Callback {
                        override fun onSuccess() {
                            val imageBitmap = (imageView?.drawable as BitmapDrawable).bitmap
                            val imageDrawable = RoundedBitmapDrawableFactory.create(
                                resources, imageBitmap
                            )
                            imageDrawable.isCircular = true
                            imageDrawable.cornerRadius =
                                max(imageBitmap.width, imageBitmap.height) / 2.0f
                            imageView!!.setImageDrawable(imageDrawable)
                        }

                        override fun onError(e: Exception?) {
                            imageView?.setImageResource(R.drawable.ic_launcher_foreground)
                        }

                    })
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("failed in main", it) }
            }
        }
    }
}