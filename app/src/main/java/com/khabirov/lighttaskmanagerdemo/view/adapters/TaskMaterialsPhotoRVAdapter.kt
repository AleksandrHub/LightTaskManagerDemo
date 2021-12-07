package com.niww.lighttaskmanager.view.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.ProfileMenuModel
import kotlinx.android.synthetic.main.photo_item.view.*
import kotlinx.android.synthetic.main.profile_menu_item.view.*

class TaskMaterialsPhotoRVAdapter(
    private val data: MutableList<Bitmap> = mutableListOf()
) :
    RecyclerView.Adapter<TaskMaterialsPhotoRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(image: Bitmap) {
        this.data.add(image)
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(image: Bitmap) {
            view.photo_item_image.setImageBitmap(image)
        }
    }
}