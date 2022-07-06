package com.arikc.wallpapermatch

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import com.arikc.wallpapermatch.model.Photo
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Integer.max


class PhotoListViewAdapter(private val viewPhotos: List<Photo>, private val context: Context) :
    RecyclerView.Adapter<PhotoListViewAdapter.PhotoHolder>() {

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myView = itemView
        var myImageView: ImageView = myView.findViewById(R.id.photoImageView)
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view: View = layoutInflater.inflate(
            R.layout.photo_list_item,
            parent,
            false
        )
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photo = viewPhotos[position]
        Picasso.get().load(photo.urls.regular).into(holder.myImageView, object : Callback {
            override fun onSuccess() {
                val imageBitmap = (holder.myImageView.drawable as BitmapDrawable).bitmap
                val imageDrawable = RoundedBitmapDrawableFactory.create(
                    context.resources, imageBitmap
                )
                imageDrawable.isCircular = true
                imageDrawable.cornerRadius =
                    max(imageBitmap.width, imageBitmap.height) / 15.0f
                holder.myImageView.setImageDrawable(imageDrawable)
            }

            override fun onError(e: java.lang.Exception?) {
                holder.myImageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        })
    }

    override fun getItemCount(): Int {
        return viewPhotos.count()
    }
}

